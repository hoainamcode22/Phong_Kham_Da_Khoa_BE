package com.example.phong_kham_da_khoa.dto;

import com.example.phong_kham_da_khoa.Model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String name;
    private Role role;
}
