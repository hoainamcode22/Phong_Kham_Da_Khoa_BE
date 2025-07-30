package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import com.example.phong_kham_da_khoa.Security.JwtUtil;
import com.example.phong_kham_da_khoa.dto.AuthResponse;
import com.example.phong_kham_da_khoa.dto.LoginRequest;
import com.example.phong_kham_da_khoa.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Tạo user mới
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // Lưu vào database
        userRepository.save(user);

        // Tạo token
        String token = jwtUtil.generateToken(user);


        return new AuthResponse(token, user.getName(), user.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        // Tìm user theo email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Tạo token
        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token, user.getName(), user.getRole());
    }
}
