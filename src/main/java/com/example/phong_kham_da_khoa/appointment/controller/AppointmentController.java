package com.example.phong_kham_da_khoa.appointment.controller;

import com.example.phong_kham_da_khoa.appointment.dto.AppointmentRequest;
import com.example.phong_kham_da_khoa.appointment.dto.AppointmentResponse;
import com.example.phong_kham_da_khoa.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Đặt lịch khám
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    // Lấy lịch của chính tôi (khách hàng hoặc nha sĩ)
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CUSTOMER','DENTIST')")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {
        return ResponseEntity.ok(appointmentService.getMyAppointments());
    }
}
