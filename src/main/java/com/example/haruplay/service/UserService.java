package com.example.haruplay.service;

import com.example.haruplay.domain.User;
import com.example.haruplay.projection.UserInfoProjection;
import com.example.haruplay.repository.UserRepository;
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
