package com.example.application.dto.response;

import com.example.application.dto.value.TokenType;
import com.example.application.value.UserType;

public record LoginResponse(String token, TokenType tokenType, UserType userType) {
}
