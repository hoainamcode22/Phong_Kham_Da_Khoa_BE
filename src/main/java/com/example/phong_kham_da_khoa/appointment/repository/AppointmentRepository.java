package com.example.phong_kham_da_khoa.appointment.repository;

import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByPatient(User patient);

    boolean existsByDoctorAndAppointmentTime(User doctor, LocalDateTime appointmentTime);
    boolean existsByPatientAndAppointmentTime(User patient, LocalDateTime appointmentTime);
}
