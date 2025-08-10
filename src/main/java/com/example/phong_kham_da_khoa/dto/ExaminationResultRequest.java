package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

@Data
public class ExaminationResultRequest {
    private Long appointmentId;
    private String resultText;
}
