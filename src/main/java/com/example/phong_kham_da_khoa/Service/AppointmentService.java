package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.dto.AppointmentRequest;
import com.example.phong_kham_da_khoa.Model.Appointment;
import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.Repository.ClinicRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Scheduled; // thư viện nhắc lịch hẹn
import java.time.LocalDate;
import java.util.List;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email).orElseThrow();

        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow();
        Clinic clinic = clinicRepository.findById(request.getClinicId()).orElseThrow();

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setRecurring(request.isRecurring());
        appointment.setNotes(request.getNotes());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setClinic(clinic);

        return appointmentRepository.save(appointment);
    }

//nhăắc lịch khám mock tự động
    @Scheduled(cron = "0 0 8 * * *") // Mỗi ngày lúc 8h sáng
    public void sendReminderForTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Appointment> appointments = appointmentRepository.findAll();

        appointments.stream()
                .filter(a -> a.getAppointmentTime().toLocalDate().isEqual(tomorrow))
                .forEach(a -> {
                    System.out.println("[NOTIFY] Nhắc lịch khám cho: " + a.getPatient().getEmail()
                            + " vào " + a.getAppointmentTime());
                });
    }

}
