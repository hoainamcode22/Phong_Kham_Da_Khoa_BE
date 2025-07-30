package com.example.phong_kham_da_khoa.dto;

import com.example.phong_kham_da_khoa.Model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
