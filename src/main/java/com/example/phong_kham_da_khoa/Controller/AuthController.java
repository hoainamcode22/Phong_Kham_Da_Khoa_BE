package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.Service.AuthService;
import com.example.phong_kham_da_khoa.dto.AuthResponse;
import com.example.phong_kham_da_khoa.dto.LoginRequest;
import com.example.phong_kham_da_khoa.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }


}
