package com.ljy.jwt.auth;

import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class PrincipalUserDetailsService implements UserDetailsService {
    
    private final MemberRepository memberRepository;
    
    private Member member;
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Member member  = memberRepository.selectMember(username);
        HashMap<String,String> payloadMap = new HashMap<>();
        payloadMap.put("sub" , member.getEmail());
        return new PrincipalUserDetails(username);
    }
}
