package com.ljy.jwt.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * REDIS 관련 설정하는 클래스
 */
@Configuration
public class RedisConfig {
    
    @Value("${redis.host}")
    private String redisHost;
    
    @Value("${redis.port}")
    private int redisPort;
    
    
    /**
     * RedisConnectionFactory
     * redis서버와의 통신을 위한 low-level 추상화를 제공
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

    return new LettuceConnectionFactory(redisHost,redisPort);
        }
        
        
    /**
     * RedisTemplate
     * redis 서버에 redis command를 수행하기 위한 high-level 추상화를 제공
     */
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        
        return redisTemplate;
    }
    
    /**
     * 저장된 키와 값 읽기
     * StringRedisSerializer 을 이용해서 저장된 키와 값을 읽읅 수 있다.
     */
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        return stringRedisTemplate;
    }
}
