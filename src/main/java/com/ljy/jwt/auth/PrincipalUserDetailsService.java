package com.ljy.jwt.auth;

import com.ljy.jwt.common.ErrorCode;
import com.ljy.jwt.handler.CustomException;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

   @Autowired(required = false)
    MemberRepository memberRepository;
    
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        //username 이 loginId 이다.
        Member member = memberRepository.selectMember(username);
        System.out.println("member"+member);
        if (member.getUsername() == null || member.getUsername().isEmpty()) {
            System.out.println("존재하지 않는 아이디입니다.");
        }
        return new PrincipalUserDetails(member);

    }
    
//        @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> member = Optional.ofNullable(memberRepository.selectMember(email));
//        member.orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
//        );
//        return (UserDetails) member.get();
//    }
}
