package com.ljy.jwt.auth;

import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//@RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {
   
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
    
    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> member = MemberRepository.selectUser(email);
//        member.orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
//        );
//        return member.get();
//    }
}
