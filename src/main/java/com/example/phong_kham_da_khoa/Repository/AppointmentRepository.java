package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.Appointment;
import com.example.phong_kham_da_khoa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByPatient(User patient);
}
