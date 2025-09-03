package com.example.phong_kham_da_khoa.doctor.controller;

import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.doctor.service.DoctorService;
import com.example.phong_kham_da_khoa.doctor.dto.DoctorRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CLINIC_OWNER')")
    public ResponseEntity<User> createDoctor(@RequestBody @Valid DoctorRegisterRequest request) {
        return ResponseEntity.ok(doctorService.registerDoctor(request));
    }
}
