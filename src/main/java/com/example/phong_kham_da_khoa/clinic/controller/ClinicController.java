package com.example.phong_kham_da_khoa.clinic.controller;

import jakarta.validation.Valid;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.clinic.dto.ClinicRequest;
import com.example.phong_kham_da_khoa.clinic.service.ClinicService;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping({"/api/clinics", "/api/clinic"})
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService ClinicService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('CLINIC_OWNER')")
    public ResponseEntity<?> createClinic(@RequestBody @Valid ClinicRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email).orElse(null);
        if (owner == null) {
            return ResponseEntity.badRequest().body("Không xác định được chủ phòng khám");
        }

        Clinic clinic = ClinicService.createClinic(
                request.getName(),
                request.getAddress(),
                request.getPhone(),
                request.getOpenTime(),
                request.getCloseTime(),
                owner
        );
        return ResponseEntity.ok(clinic);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Clinic>> getPendingClinics() {
        return ResponseEntity.ok(ClinicService.getAllPendingClinics());
    }

    // PUBLIC / SEARCH APPROVED CLINICS (pagination)
    // GET /api/clinics/approved?page=0&size=10&sortBy=name&sortDir=asc
    @GetMapping("/approved")
    public ResponseEntity<Page<Clinic>> searchApprovedClinics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return ResponseEntity.ok(
                ClinicService.getApprovedClinics(PageRequest.of(page, size, sort))
        );
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveClinic(@PathVariable Long id) {
        return ResponseEntity.ok(ClinicService.approveClinic(id));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectClinic(@PathVariable Long id) {
        return ResponseEntity.ok(ClinicService.rejectClinic(id));
    }
}
