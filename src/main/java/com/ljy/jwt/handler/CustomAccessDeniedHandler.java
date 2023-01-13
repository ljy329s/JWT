package com.ljy.jwt.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.jwt.common.ErrorCode;
import com.ljy.jwt.common.ResponseVO;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseVO responseVO = ResponseVO.builder()
            .status(HttpStatus.FORBIDDEN)
            .message("Forbidden")
            .code(ErrorCode.FORBIDDEN.getCode())
            .build();
        
        String result = objectMapper.writeValueAsString(responseVO);
        
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.getWriter().write(result);
        httpServletResponse.getWriter().flush();
    }
}
