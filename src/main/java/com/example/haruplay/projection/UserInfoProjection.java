package com.example.haruplay.projection;

import java.time.LocalDateTime;

public interface UserInfoProjection {
    String getUserEmail();
    String getNickname();
    LocalDateTime getRegTm();
}
