package com.ljy.jwt.model.controller;

import com.ljy.jwt.jwt.TokenProvider;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final TokenProvider tokenProvider;
    
    @Autowired
    private MemberRepository memberRepository;
    @PostMapping("/login")
    public String login(){
    Member member1 = memberRepository.selectMember("bob1234");
        tokenProvider.createAccessToken(member1);
        return "로그인 메소드";
    }
}
