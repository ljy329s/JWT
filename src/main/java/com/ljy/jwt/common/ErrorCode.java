package com.ljy.jwt.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN-0001", "Access token has expired");
    
    private HttpStatus status;
    private String code;
    private String message;
    
    
    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
