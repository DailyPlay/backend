package com.example.haruplay.global.security;

import com.example.haruplay.user.UserEntity;
import com.example.haruplay.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && tokenProvider.validateToken(token)) {

            String userEmail = tokenProvider.getUserEmail(token);
            log.info("Token validated successfully for userEmail: {}", userEmail);

            UserEntity userEntity = userService.getUserById(Long.valueOf(userEmail));// UserEntity 변경 시 수정 필요

            UserDetails userDetails = new UserDetailsImpl(userEntity);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication set for userEmail: {}", userEmail);

        } else {
            log.warn("Invalid JWT token: {}", token);

            String refreshToken = getRefreshTokenFromRequest(request);
            if (refreshToken != null && tokenProvider.validateRefreshToken(refreshToken)) {
                log.info("Valid refresh token found: {}", refreshToken);

                String userEmail = tokenProvider.getUserEmail(refreshToken);
                String newAccessToken = tokenProvider.createAccessToken(userEmail);

                response.setHeader("Authorization", "Bearer " + newAccessToken);

                setAuthentication(newAccessToken, request);
            } else {
                log.warn("Invalid JWT token or Refresh token: {}", token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletRequest request) {
        String userEmail = tokenProvider.getUserEmail(token);
        log.info("Token validated successfully for user ID: {}", userEmail);

        UserEntity userEntity = userService.getUserById(Long.valueOf(userEmail));

        UserDetails userDetails = new UserDetailsImpl(userEntity);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authentication set for user : {}", userEntity);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Refresh-Token");
    }
}
