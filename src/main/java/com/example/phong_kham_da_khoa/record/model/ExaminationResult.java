package com.example.phong_kham_da_khoa.record.model;

import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "examination_result")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ExaminationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String resultText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
}
