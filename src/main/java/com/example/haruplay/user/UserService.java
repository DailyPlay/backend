package com.example.haruplay.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserEntity getUserById(Long id) {
        return userMapper.findById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userMapper.findAll();
    }

    public void createUser(UserEntity user) {
        userMapper.insert(user);
    }

    public void updateUser(UserEntity user) {
        userMapper.update(user);
    }

    public void deleteUser(Long id) {
        userMapper.delete(id);
    }
}
