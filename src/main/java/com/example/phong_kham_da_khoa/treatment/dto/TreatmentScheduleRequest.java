package com.example.phong_kham_da_khoa.treatment.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class TreatmentScheduleRequest {
    @NotNull(message = "doctorId is required")
    private Long doctorId;

    @NotNull(message = "clinicId is required")
    private Long clinicId;

    @NotNull(message = "startDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @FutureOrPresent(message = "startDate must be in the present or future")
    private LocalDateTime startDate;

    @Min(value = 1, message = "totalSessions must be >= 1")
    private int totalSessions;

    @Min(value = 1, message = "intervalDays must be >= 1")
    private int intervalDays;

    private String notes; // optional
}
