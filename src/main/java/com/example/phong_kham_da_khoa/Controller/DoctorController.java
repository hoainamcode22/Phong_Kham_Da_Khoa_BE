package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.dto.DoctorRegisterRequest;
import com.example.phong_kham_da_khoa.Service.DoctorService;
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
    public ResponseEntity<User> createDoctor(@RequestBody DoctorRegisterRequest request) {
        return ResponseEntity.ok(doctorService.registerDoctor(request));
    }
}
