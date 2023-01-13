package com.ljy.jwt.config.security;

import com.ljy.jwt.auth.PrincipalUserDetailsService;
import com.ljy.jwt.handler.CustomAccessHandler;
import com.ljy.jwt.jwt.JwtAuthenticationFilter;
import com.ljy.jwt.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 시큐리티 설정을 담당하는 클래스
 */
@EnableWebSecurity//시큐리티활성화 어노테이션 내부에 configuration 어노테이션이 있다
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfig {
    
   // private final PrincipalUserDetailsService principalUserDetailsService;
    
    private final TokenProvider tokenProvider;
    
    private final AuthenticationConfiguration authenticationConfiguration;
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("securityConfig");
        return http
            .httpBasic().disable()
            .cors()
            .and()
            .csrf().disable()
//            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션사용안함
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new CustomAccessHandler())
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .build();
            }
}
