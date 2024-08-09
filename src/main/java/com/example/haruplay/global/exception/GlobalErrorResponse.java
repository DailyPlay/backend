package com.example.haruplay.global.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalErrorResponse {
    private String error;
    private String message;

    public GlobalErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
