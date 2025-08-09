package com.example.demo.dto;

import lombok.Data;

@Data
public class ExaminationResultRequest {
    private Long appointmentId;
    private String resultText;
}
