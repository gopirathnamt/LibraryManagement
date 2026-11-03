package com.example.application.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

@Component
public class JOSEAuthenticationConverter implements AuthenticationConverter {
    @Override
    public @Nullable Authentication convert(@NonNull HttpServletRequest request) {
        return null;
    }
}
