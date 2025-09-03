package com.example.phong_kham_da_khoa.record.repository;

import com.example.phong_kham_da_khoa.record.model.ExaminationResult;
import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExaminationResultRepository extends JpaRepository<ExaminationResult, Long> {
    List<ExaminationResult> findByPatient(User patient);

    boolean existsByAppointment(Appointment appointment);

    Optional<ExaminationResult> findByAppointment(Appointment appointment);
}
