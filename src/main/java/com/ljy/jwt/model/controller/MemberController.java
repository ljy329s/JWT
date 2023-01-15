package com.ljy.jwt.model.controller;

import com.ljy.jwt.jwt.TokenProvider;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    
    private final TokenProvider tokenProvider;
    
    @Autowired(required = false)
    private MemberRepository memberRepository;

//    @PostMapping("/login")
//    public String login(){
//    Member member1 = memberRepository.selectMember("test123");
//        System.out.println("POST 로그인");
//        tokenProvider.createAccessToken(member1);
//        return "로그인 메소드";
//    }

}
