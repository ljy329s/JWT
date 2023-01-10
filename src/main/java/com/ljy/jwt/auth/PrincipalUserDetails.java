package com.ljy.jwt.auth;

import com.ljy.jwt.model.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PrincipalUserDetails implements UserDetails {
    
    private String userName;
    
    public PrincipalUserDetails(String userName) {
        this.userName = userName;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    @Override
    public String getPassword() {
        return null;
    }
    
    @Override
    public String getUsername() {
        return null;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}
