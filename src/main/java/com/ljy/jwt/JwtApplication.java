package com.ljy.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Target;

@SpringBootApplication
public class JwtApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }
    
}
