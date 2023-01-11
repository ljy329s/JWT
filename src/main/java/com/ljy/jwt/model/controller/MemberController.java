package com.ljy.jwt.model.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one")
public class MemberController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/new")
    public String newToken(){
        return "new ";
    }
}
