package com.example.application.service;

import com.example.application.dto.request.LoginRequest;
import com.example.application.dto.response.LoginResponse;
import com.example.application.model.CreateUserRequest;

public interface ILoginService {

    LoginResponse login(LoginRequest loginRequest);

    String create(CreateUserRequest userRequest);
}
