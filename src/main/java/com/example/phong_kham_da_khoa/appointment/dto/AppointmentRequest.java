package com.example.phong_kham_da_khoa.appointment.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @NotNull(message = "doctorId is required")
    private Long doctorId;

    @NotNull(message = "clinicId is required")
    private Long clinicId;

    @NotNull(message = "appointmentTime is required")
    // @Future(message = "appointmentTime must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentTime;

    private boolean recurring; // optional
    private String notes;      // optional
}
