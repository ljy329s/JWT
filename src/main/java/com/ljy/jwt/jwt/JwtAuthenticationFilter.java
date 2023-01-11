package com.ljy.jwt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.jwt.common.ErrorCode;
import com.ljy.jwt.common.ResponseVO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 모든 api 가 실행되기 전에 실행되는 Filter 이다
 * ACCESS TOKEN 을 재발급하는 경우에는 토큰을 체크하는 로직을 태우지 않는다.
 * 헤더로부터 가져온 Access token 이 유효한지 체크를 하고 유효하다면 SecurityContext 에 저장한다.
 * ExpiredJwtException 은 체크하는 토큰이 만료가 된 경우의 Exception
 * Filter 에서 발생하는 Exception 은 Custon 할 수가 없다.
 * HttpServletResponse 에 Access token 만료를 알리는 Custom 400에러를 담아 보내줌
 */


public class JwtAuthenticationFilter extends OncePerRequestFilter {//OncePerRequestFilter : 모든서블릿에 일관된 요청을 하기 위해 만들어진필터
    
    private final TokenProvider jwtTokenProvider;
    
    public JwtAuthenticationFilter(TokenProvider provider) {
        jwtTokenProvider = provider;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(response+"response");
        System.out.println("JwtAuthenticationFilter");
        String path = request.getServletPath();
        System.out.println("path : "+path);
        try {
            if(path.startsWith("/api/auth/reissue")) {//토큰을 재발급하는 경우 토큰체크로직 건너뛰기 startsWith : 특정 문자열로 시작하는지 알수있는 함수 해당문자열로 시작되는지 확인하고 boolean으로 값 리턴(공백도 인식함 유의)
                filterChain.doFilter(request, response);
                System.out.println("1");
            } else {
                String accessToken = jwtTokenProvider.resolveAccessToken(request);
                System.out.println("accessToken : "+accessToken);
                boolean isTokenValid = jwtTokenProvider.validateToken(accessToken, request);
                
                if (StringUtils.hasText(accessToken) && isTokenValid) {
                    this.setAuthentication(accessToken);
                }
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            ResponseVO responseVO = ResponseVO.builder()
                .status(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getStatus())
                .message(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getMessage())
                .code(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getCode())
                .build();
            response.setStatus(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getStatus().value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseVO));
            response.getWriter().flush();
            
        }
    }
    
    /**
     * 헤더로부터 가져온 정보가 유효하다면 SecurityContext 의 Authentication 에 저장
     */
    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
