package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.Model.Role;
import com.example.phong_kham_da_khoa.dto.AppointmentRequest;
import com.example.phong_kham_da_khoa.Model.Appointment;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import com.example.phong_kham_da_khoa.Service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    // Đặt lịch khám
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Appointment> create(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DENTIST')")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow();

        List<Appointment> appointments;

        if (currentUser.getRole() == Role.CUSTOMER) {
            appointments = appointmentRepository.findByPatient(currentUser);
        } else if (currentUser.getRole() == Role.DENTIST) {
            appointments = appointmentRepository.findByDoctor(currentUser);
        } else {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(appointments);
    }
}
