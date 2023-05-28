package com.uber.auth.controller;

import com.uber.auth.entity.UserDto;
import com.uber.auth.service.UserService;
import com.uber.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/user/register"})
    public User register(@RequestBody UserDto userDto) {
        User user = User.builder().username(userDto.getUsername()).password(userDto.getPassword()).build();
        return userService.registerAsUser(user);
    }


    @GetMapping({"/validateUser"})
    @PreAuthorize("hasRole('User')")
    public Boolean forUser(){
        return true;
    }
}
