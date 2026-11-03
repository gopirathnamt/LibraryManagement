package com.example.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = {"", "/", "/home"})
    public String hello() {
        return "Hello";
    }

    @GetMapping(value = "/logout/success")
    public String afterLogout() {
        System.out.println("Logged out");
        return "Performed Logout";
    }
}
