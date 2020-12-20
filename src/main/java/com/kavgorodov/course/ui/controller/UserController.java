package com.kavgorodov.course.ui.controller;

import com.kavgorodov.course.service.UserService;
import com.kavgorodov.course.shared.dto.UserDto;
import com.kavgorodov.course.ui.model.request.UserDetailsRequestModel;
import com.kavgorodov.course.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Vishnevskiy
 */

@RestController
@RequestMapping("/users")
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
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "Update methode is called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "Delete methode is called";
    }

}
