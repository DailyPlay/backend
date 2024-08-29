package com.example.haruplay.global.security;

import com.example.haruplay.user.UserEntity;
import com.example.haruplay.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userEmail;
        try {
            userEmail = authentication.getPrincipal().toString();
        } catch (NumberFormatException e) {
            throw new BadCredentialsException("Invalid user ID format");
        }

        UserEntity userEntity = userService.getUserById(Long.valueOf(userEmail)); // UserEntity 변경 시 수정 필요

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with ID: " + userEmail);
        }

        // 패스워드 없이 인증 수행
        return new UsernamePasswordAuthenticationToken(userEntity, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}