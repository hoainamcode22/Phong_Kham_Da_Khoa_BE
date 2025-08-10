package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvailableSlotRequest {
    private Long doctorId;
    private Long clinicId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
