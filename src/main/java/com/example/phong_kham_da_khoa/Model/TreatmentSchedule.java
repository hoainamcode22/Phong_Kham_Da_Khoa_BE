package com.example.phong_kham_da_khoa.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@Entity
public class TreatmentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private int totalSessions;

    private int intervalDays; // Số ngày giữa các buổi điều trị

    private String notes;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
