package com.example.phong_kham_da_khoa.auth.dto;

import com.example.phong_kham_da_khoa.user.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String name;
    private String role;

    public AuthResponse(String token, String name, Role role) {
        this.token = token;
        this.name = name;
        this.role = (role != null ? role.name() : null);
    }
}
