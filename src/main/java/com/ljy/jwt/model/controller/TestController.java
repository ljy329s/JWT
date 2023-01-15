package com.ljy.jwt.model.controller;

import com.ljy.jwt.auth.PrincipalUserDetailsService;
import com.ljy.jwt.jwt.TokenProvider;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.domain.Token;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/testApi")
@RequiredArgsConstructor
public class TestController {

    private final TokenProvider tokenProvider;

    private final PrincipalUserDetailsService principalUserDetailsService;


    // 로그인
    @PostMapping("/login")
    public Token login(@RequestBody Map<String, String> Login) {
        String username = Login.get("username");
        System.out.println("username :" +username);
        System.out.println("Map:"+Login);
      Member mem = (Member) principalUserDetailsService.loadUserByUsername(username);
//        Member mem = memberRepository.selectMember(id);//member.get("username")
//        Long time = null;
        return tokenProvider.createToken(mem);
    }
}
