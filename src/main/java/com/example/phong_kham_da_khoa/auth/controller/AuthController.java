package com.example.phong_kham_da_khoa.auth.controller;

import com.example.phong_kham_da_khoa.auth.service.AuthService;
import com.example.phong_kham_da_khoa.auth.dto.AuthResponse;
import com.example.phong_kham_da_khoa.auth.dto.LoginRequest;
import com.example.phong_kham_da_khoa.auth.dto.RegisterRequest;
import jakarta.validation.Valid; // <— thêm import
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
