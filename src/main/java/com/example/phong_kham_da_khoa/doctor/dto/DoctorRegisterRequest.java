package com.example.phong_kham_da_khoa.doctor.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class DoctorRegisterRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @NotNull(message = "clinicId is required")
    private Long clinicId;
}
