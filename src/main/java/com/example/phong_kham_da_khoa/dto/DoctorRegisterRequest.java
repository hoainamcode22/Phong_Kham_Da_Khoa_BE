package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

@Data
public class DoctorRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Long clinicId;
}
