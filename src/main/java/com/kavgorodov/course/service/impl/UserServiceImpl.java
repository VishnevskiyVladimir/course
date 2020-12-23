package com.kavgorodov.course.service.impl;

import com.kavgorodov.course.exception.UserServiceException;
import com.kavgorodov.course.io.entity.UserEntity;
import com.kavgorodov.course.io.repository.UserRepository;
import com.kavgorodov.course.service.UserService;
import com.kavgorodov.course.shared.Utils;
import com.kavgorodov.course.shared.dto.AddressDTO;
import com.kavgorodov.course.shared.dto.UserDto;
import com.kavgorodov.course.ui.model.response.ErrorMessages;
import com.kavgorodov.course.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Vishnevskiy
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new UserServiceException("Record already exists");

        for(int i=0;i<user.getAddresses().size();i++)
        {
            AddressDTO address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue  = modelMapper.map(storedUserDetails, UserDto.class);

        //amazonSES.verifyEmail(returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) {throw new UsernameNotFoundException(email);}
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String UserId) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        if(userEntity == null) {throw new UsernameNotFoundException("User with ID: " + UserId + " is not found.");}
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String UserId, UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        if(userEntity == null) {throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());}

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String UserId) {
        UserEntity userEntity = userRepository.findByUserId(UserId);
        if(userEntity == null) {throw new UsernameNotFoundException(UserId);}
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for(UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) {throw new UsernameNotFoundException(email);}
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
    }


}
