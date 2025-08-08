package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private Long clinicId;
    private LocalDateTime appointmentTime;
    private boolean recurring; // true nếu là định kỳ
    private String notes;
}
