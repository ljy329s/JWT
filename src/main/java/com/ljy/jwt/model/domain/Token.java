package com.ljy.jwt.model.domain;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@RedisHash("auth")
@NoArgsConstructor
public class Token {
    
    private String key;
    
    private String value;
    
    @TimeToLive
    private Long expiredTime;

    
    @Builder
    public Token(String key, String value, Long expiredTime){
        this.key = key;
        this.value = value;
        this.expiredTime = expiredTime;
    }
}
