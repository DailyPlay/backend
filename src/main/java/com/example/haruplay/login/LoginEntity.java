package com.example.haruplay.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginEntity {
    private String userEmail;
    private SnsType snsType;
}