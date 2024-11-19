package com.example.haruplay.User.service;

import com.example.haruplay.User.entity.User;
import com.example.haruplay.User.dto.UserInfoProjection;
import com.example.haruplay.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUserInfo(){
        return userRepository.findAll();
    }
    public List<UserInfoProjection> getUserInfo() {
        return userRepository.getUserInfo();
    }
}
