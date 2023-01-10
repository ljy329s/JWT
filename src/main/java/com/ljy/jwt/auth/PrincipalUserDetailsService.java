package com.ljy.jwt.auth;

import com.ljy.jwt.common.ErrorCode;
import com.ljy.jwt.handler.CustomException;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalUserDetailsService implements UserDetailsService {
    
    private final MemberRepository memberRepository;
    
    private Member member;
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = MemberRepository.selectUser(email);
        member.orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
        );
        return member.get();
    }
}
