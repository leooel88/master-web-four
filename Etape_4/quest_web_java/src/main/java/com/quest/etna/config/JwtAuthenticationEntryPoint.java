package com.quest.etna.config;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                    if (request.getHeader("Authorization") != null) {
                        response.setStatus(401);
                        Map<String,String> payload = new HashMap<>();
                        payload.put("error","Invalid or non existing JWT");
                        String msg = new ObjectMapper().writeValueAsString(payload);
                        response.getWriter().print(msg);
                    } else {
                        response.setStatus(400);
                        Map<String,String> payload = new HashMap<>();
                        payload.put("error","invalid request");
                        String msg = new ObjectMapper().writeValueAsString(payload);
                        response.getWriter().print(msg);
                    }
    }
    
}
