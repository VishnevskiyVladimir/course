package com.kavgorodov.course.service;

import com.kavgorodov.course.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Vladimir Vishnevskiy
 */
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
}
