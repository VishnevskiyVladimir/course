package com.kavgorodov.course.service;

import com.kavgorodov.course.shared.dto.UserDto;

/**
 * @author Vladimir Vishnevskiy
 */
public interface UserService {
    UserDto createUser(UserDto user);
}
