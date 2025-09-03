package com.example.phong_kham_da_khoa.auth.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
