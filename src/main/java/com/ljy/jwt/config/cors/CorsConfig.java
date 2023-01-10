package com.ljy.jwt.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * cors 설정을 담당하는 클래스
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Value("${jwt.responseHeader}")
    private String jwtHeader;
    
    /**
     * exposedHeaders 부분으로 API 요청 시 Header에 'Authorization' 이름의 값은 받아온다
     */
    @Bean
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:9064")
            .exposedHeaders(jwtHeader)//yml 에서 header 로 설정한 Authorization 헤더 값을 받아온다.
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
            )
            .allowCredentials(true);
        }
    }

