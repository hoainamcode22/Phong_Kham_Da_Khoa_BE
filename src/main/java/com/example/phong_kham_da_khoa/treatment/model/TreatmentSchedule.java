package com.example.phong_kham_da_khoa.treatment.model;

import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_schedule")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TreatmentSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "interval_days", nullable = false)
    private int intervalDays;

    @Column(name = "total_sessions", nullable = false)
    private int totalSessions;

    @Column(name = "notes", length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
}
