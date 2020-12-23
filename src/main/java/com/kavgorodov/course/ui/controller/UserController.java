package com.kavgorodov.course.ui.controller;

import com.kavgorodov.course.exception.UserServiceException;
import com.kavgorodov.course.service.UserService;
import com.kavgorodov.course.shared.dto.UserDto;
import com.kavgorodov.course.ui.model.request.UserDetailsRequestModel;
import com.kavgorodov.course.ui.model.response.ErrorMessages;
import com.kavgorodov.course.ui.model.response.OperationStatusModel;
import com.kavgorodov.course.ui.model.response.RequestOperationStatus;
import com.kavgorodov.course.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Vishnevskiy
 */

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
// Можно настроить на XML весь контроллер, а можно только один endpoint
// Можно ограничить кол-во медиатайпов указав их массивом {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
// В таком случае если медиатайп не указан в хедере, контроллер вернет первый медиатайп
// Точно также можно настроиь consumes

//    Возможно не лучшая идея настраивать контроллер целиком, поскольку некоторые методы не потребляют тело.(Это будет видно в документации)
class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        //Don't confuse empty with null
        if(userDetails.getEmail()==null) throw new NullPointerException("The object is null");
        if(userDetails.getEmail().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


        UserRest returnValue = new UserRest();

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue =  "0") int page,
                                   @RequestParam(value = "limit", defaultValue =  "25") int limit){
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);
        for(UserDto userDto : users) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

}
