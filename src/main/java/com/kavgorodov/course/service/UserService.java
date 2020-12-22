package com.kavgorodov.course.service;

import com.kavgorodov.course.shared.dto.UserDto;
import com.kavgorodov.course.ui.model.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Vladimir Vishnevskiy
 */
public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserId(String UserId);

    UserDto updateUser(String UserId, UserDto userDto);

    void deleteUser(String UserId);

    List<UserDto> getUsers(int page, int limit);
}
