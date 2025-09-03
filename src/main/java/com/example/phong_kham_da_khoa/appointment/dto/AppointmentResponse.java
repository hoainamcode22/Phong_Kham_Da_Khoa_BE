package com.example.phong_kham_da_khoa.appointment.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentTime;
    private boolean recurring;
    private String notes;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;

    private Long clinicId;
    private String clinicName;
}
