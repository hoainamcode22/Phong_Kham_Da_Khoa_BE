package com.example.phong_kham_da_khoa.admin.controller;

import com.example.phong_kham_da_khoa.admin.dto.ApprovalRequest;
import com.example.phong_kham_da_khoa.admin.service.AdminService;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ===== Doctors =====

    @GetMapping("/pending-doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getPendingDoctors() {
        return ResponseEntity.ok(adminService.getPendingDoctors());
    }

    // Back-compat: /approve-doctor/{id}
    @PostMapping(value = {"/approve-doctor/{id}", "/dentists/{id}/approve"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveDoctor(@PathVariable Long id) {
        adminService.approveDoctor(id);
        return ResponseEntity.ok("Bác sĩ đã được duyệt.");
    }

    // Back-compat: /reject-doctor/{id}
    @PostMapping(value = {"/reject-doctor/{id}", "/dentists/{id}/reject"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectDoctor(@PathVariable Long id,
                                               @RequestBody(required = false) @Valid ApprovalRequest req) {
        String reason = (req != null ? req.getReason() : null);
        adminService.rejectDoctor(id, reason);
        return ResponseEntity.ok("Bác sĩ đã bị từ chối.");
    }

    // ===== Clinics (optional – nếu duyệt clinic tại Admin) =====

    @GetMapping("/clinics/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Clinic>> getPendingClinics() {
        return ResponseEntity.ok(adminService.getPendingClinics());
    }

    @PostMapping("/clinics/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveClinic(@PathVariable Long id) {
        adminService.approveClinic(id);
        return ResponseEntity.ok("Phòng khám đã được duyệt.");
    }

    @PostMapping("/clinics/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectClinic(@PathVariable Long id,
                                               @RequestBody(required = false) @Valid ApprovalRequest req) {
        String reason = (req != null ? req.getReason() : null);
        adminService.rejectClinic(id, reason);
        return ResponseEntity.ok("Phòng khám đã bị từ chối.");
    }
}
