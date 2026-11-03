package com.example.application.controller;

import com.example.application.dto.request.LoginRequest;
import com.example.application.dto.response.LoginResponse;
import com.example.application.model.CreateUserRequest;
import com.example.application.service.ILoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final ILoginService service;
    private final HttpServletRequest request;

    @Autowired
    public LoginController(ILoginService service, HttpServletRequest request) {
        this.service = service;
        this.request = request;
    }

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws ServletException {
        LoginResponse token = service.login(loginRequest);
        request.login(loginRequest.username(), loginRequest.password());
        return token;
    }

    @GetMapping
    public LoginResponse login(@RequestParam String username, @RequestParam String password) throws ServletException {
        return login(new LoginRequest(username, password));
    }

    @PostMapping("/create")
    public String createUser(@RequestBody CreateUserRequest userRequest) {
        return service.create(userRequest);
    }

    @GetMapping("/logout")
    public String logout() throws ServletException {
        request.logout();
        return "User logged out";
    }

}
