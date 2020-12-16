package com.kavgorodov.course.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Vishnevskiy
 */

@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping
    public String getUser() {
        return "Read methode is called";
    }

    @PostMapping
    public String createUser() {
        return "Create methode is called";
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
