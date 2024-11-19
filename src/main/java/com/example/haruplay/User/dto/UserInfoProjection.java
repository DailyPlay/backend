package com.example.haruplay.User.dto;

import java.time.LocalDateTime;

public interface UserInfoProjection {
    String getUserEmail();
    String getNickname();
    LocalDateTime getRegTm();
}
