package com.example.application.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull AuthenticationException authException) throws IOException {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        response.sendRedirect("/login", HttpServletResponse.SC_UNAUTHORIZED);
    }
}
