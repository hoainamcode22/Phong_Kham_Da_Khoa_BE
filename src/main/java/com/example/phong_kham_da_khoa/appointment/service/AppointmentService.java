package com.example.phong_kham_da_khoa.appointment.service;

import com.example.phong_kham_da_khoa.appointment.dto.AppointmentRequest;
import com.example.phong_kham_da_khoa.appointment.dto.AppointmentResponse;
import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.appointment.repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    private static AppointmentResponse toDto(Appointment a) {
        return AppointmentResponse.builder()
                .id(a.getId())
                .appointmentTime(a.getAppointmentTime())
                .recurring(a.isRecurring())
                .notes(a.getNotes())
                .patientId(a.getPatient().getId())
                .patientName(a.getPatient().getName())
                .doctorId(a.getDoctor().getId())
                .doctorName(a.getDoctor().getName())
                .clinicId(a.getClinic().getId())
                .clinicName(a.getClinic().getName())
                .build();
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email).orElseThrow();

        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow();
        if (doctor.getRole() != Role.DENTIST) {
            throw new IllegalArgumentException("User is not a DENTIST");
        }

        Clinic clinic = clinicRepository.findById(request.getClinicId()).orElseThrow();
        if (clinic.getStatus() != ApprovalStatus.APPROVED) {
            throw new IllegalStateException("Clinic is not APPROVED");
        }

        // (tối thiểu) chặn book trùng giờ
        if (appointmentRepository.existsByDoctorAndAppointmentTime(doctor, request.getAppointmentTime())) {
            throw new IllegalStateException("Doctor already has an appointment at this time");
        }
        if (appointmentRepository.existsByPatientAndAppointmentTime(patient, request.getAppointmentTime())) {
            throw new IllegalStateException("You already booked an appointment at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setRecurring(request.isRecurring());
        appointment.setNotes(request.getNotes());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setClinic(clinic);

        return toDto(appointmentRepository.save(appointment));
    }

    @Transactional
    public List<AppointmentResponse> getMyAppointments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow();

        List<Appointment> list;
        if (currentUser.getRole() == Role.CUSTOMER) {
            list = appointmentRepository.findByPatient(currentUser);
        } else if (currentUser.getRole() == Role.DENTIST) {
            list = appointmentRepository.findByDoctor(currentUser);
        } else {
            throw new SecurityException("Forbidden");
        }
        return list.stream().map(AppointmentService::toDto).toList();
    }

    // Nhắc lịch khám (mock) – mỗi ngày 8h sáng
    @Scheduled(cron = "0 0 8 * * *")
    public void sendReminderForTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        appointmentRepository.findAll().stream()
                .filter(a -> a.getAppointmentTime().toLocalDate().isEqual(tomorrow))
                .forEach(a -> {
                    System.out.println("[NOTIFY] Nhắc lịch khám cho: " + a.getPatient().getEmail()
                            + " vào " + a.getAppointmentTime());
                });
    }
}
