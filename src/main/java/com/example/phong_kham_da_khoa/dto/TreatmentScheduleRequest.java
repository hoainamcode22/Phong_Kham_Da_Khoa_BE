package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TreatmentScheduleRequest {
    private Long doctorId;
    private Long clinicId;
    private LocalDateTime startDate;
    private int totalSessions; // Tổng số buổi điều trị
    private int intervalDays; // Khoảng cách ngày giữa các buổi
    private String notes;
}
