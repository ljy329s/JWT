package com.ljy.jwt.auth;

import com.ljy.jwt.model.domain.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class PrincipalUserDetails implements UserDetails , Serializable {
    
    private static final long serialVersionUID = 1L;

    private Member member;
    
    public PrincipalUserDetails(Member member){
    this.member = member;
    }
    
    /**
     * 유저가 가지고 있는 권한을 리턴해준다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRoles();
            }
        });
        return null;
    }
    
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
