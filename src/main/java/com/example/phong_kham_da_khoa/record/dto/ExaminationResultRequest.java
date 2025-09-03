package com.example.phong_kham_da_khoa.record.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ExaminationResultRequest {
    @NotNull(message = "appointmentId is required")
    private Long appointmentId;

    @NotBlank(message = "resultText is required")
    private String resultText;
}
