package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.dto.ClinicRequest;
import com.example.phong_kham_da_khoa.Service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    // Chủ phòng khám đăng ký phòng khám mới
    @PostMapping("/create")
    @PreAuthorize("hasRole('CLINIC_OWNER')")
    public ResponseEntity<?> createClinic(@RequestBody ClinicRequest request,
                                          @AuthenticationPrincipal User owner) {
        Clinic clinic = clinicService.createClinic(
                request.getName(),
                request.getAddress(),
                request.getPhone(),
                owner
        );
        return ResponseEntity.ok(clinic);
    }

    // Admin lấy danh sách phòng khám chờ duyệt
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Clinic>> getPendingClinics() {
        return ResponseEntity.ok(clinicService.getAllPendingClinics());
    }

    // Admin duyệt phòng khám
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveClinic(@PathVariable Long id) {
        Clinic clinic = clinicService.approveClinic(id);
        return ResponseEntity.ok(clinic);
    }

    // Admin từ chối phòng khám
    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectClinic(@PathVariable Long id) {
        Clinic clinic = clinicService.rejectClinic(id);
        return ResponseEntity.ok(clinic);
    }
}