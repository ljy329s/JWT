package com.ljy.jwt.jwt;


import com.ljy.jwt.auth.PrincipalUserDetailsService;
import com.ljy.jwt.model.domain.Member;
import com.ljy.jwt.model.domain.Token;
import com.ljy.jwt.util.JwtUtil;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

/**
 * 전체적인 토큰을 관리하는 클래스
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;
    
    @Value("${jwt.response-header}")
    private String jwtHeader;
    
    @Value("${jwt.prefix}")
    private String jwtTokenPrefix;

    private final PrincipalUserDetailsService principalUserDetailsService;
    
    /**
     * 엑세스토큰 만료시간 : 30분
     */
    private long accessTokenValidTime = Duration.ofMinutes(30).toMillis();//만료시간 30분
    
    
    /**
     * 리프레시토큰 만료기간 : 2주
     */
    private long refreshTokenValidTime = Duration.ofDays(14).toMillis();//만료시간 2주
    
    
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public Token createAccessToken(Member member){
        return createToken(member, accessTokenValidTime);
    }
    
    public Token createRefreshToken(Member member){
        return createToken(member, refreshTokenValidTime);
    }
    
    
    /**
     * 엑세스 토큰 생성 요청, 리프레시 토큰 생성을 처리하는 메서드
     */
    public Token createToken(Member member, long tokenValidTime){
        Claims claims = Jwts.claims().setSubject(member.getEmail());
        claims.put("roles",member.getRole());
        Date now = new Date();
        
        String token = Jwts.builder()
            .setClaims(claims)//정보
            .setIssuedAt(now)//토큰발행시간
            .setExpiration(new Date(now.getTime() + tokenValidTime))//토큰만료시간
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();// 토큰발행
        
        return Token.builder()
            .key(member.getEmail())
            .value(token)
            .expiredTime(tokenValidTime)
            .build();
    }
    
    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        HashMap<String, String> payloadMap = JwtUtil.getPayloadByToken(token);
        UserDetails userDetails = principalUserDetailsService.loadUserByUsername(payloadMap.get("sub"));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
    
    //JWT 토큰으로 회원 정보 조회
    public String getUserEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    //GET ACCESS TOKEN BY HEADER
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeader);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)){
            return bearerToken.substring(7);
        }
        return null;
    }
    
    /**
     * 토큼의 유효 및 만료여부 확인
     * UnsupportedJwtException : jwt가 예상하는 형식과 다른 형식이거나 구성
     * MalformedJwtException : 잘못된 jwt 구조
     * ExpiredJwtException : JWT 유효기간이 초과
     * SignatureException : JWT 서명실패(변조 데이터)
     */
    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error(e.getMessage());
            log.error("Invalid JWT signature");
            log.error("JWT 시크릿키가 잘못됐습니다.");
            return false;
        }catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            log.error("Unsupported JWT token");
            log.error("지원되지 않는 형식의 JWT 토큰입니다.");
            return false;
        }catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            log.error("JWT token is invalid");
            log.error("유효하지 않은 JWT 토큰입니다.");
            return false;
        }
    }
    
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(jwtHeader, accessToken);
    }

}
