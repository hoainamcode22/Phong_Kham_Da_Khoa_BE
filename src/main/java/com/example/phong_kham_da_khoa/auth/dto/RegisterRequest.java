package com.example.phong_kham_da_khoa.auth.dto;

import com.example.phong_kham_da_khoa.user.model.Role;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @NotNull(message = "role is required")
    private Role role;
}
