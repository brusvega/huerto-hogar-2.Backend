package com.example.BackEndHuerto.controller;

import com.example.BackEndHuerto.dto.AuthResponse;
import com.example.BackEndHuerto.dto.LoginRequest;
import com.example.BackEndHuerto.dto.RegisterRequest;
import com.example.BackEndHuerto.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    // Constructor manual SIN Lombok
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
