package com.example.phong_kham_da_khoa.treatment.repository;

import com.example.phong_kham_da_khoa.treatment.model.TreatmentSchedule;
import com.example.phong_kham_da_khoa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentScheduleRepository extends JpaRepository<TreatmentSchedule, Long> {
    List<TreatmentSchedule> findByPatient(User patient);
}
