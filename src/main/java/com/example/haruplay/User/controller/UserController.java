package com.example.haruplay.User.controller;

import com.example.haruplay.User.entity.User;
import com.example.haruplay.User.dto.UserInfoProjection;
import com.example.haruplay.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUserInfo();
    }

    @GetMapping("/users-detail")
    public List<UserInfoProjection> getAllMembers(){
        return userService.getUserInfo();
    }
}
