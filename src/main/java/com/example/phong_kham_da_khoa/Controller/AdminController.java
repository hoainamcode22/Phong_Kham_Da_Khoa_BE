package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.Model.ApprovalStatus;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.phong_kham_da_khoa.Model.Role; // Hoang Hà: import Role enum


import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // Xem danh sách bác sĩ chờ duyệt
    @GetMapping("/pending-doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getPendingDoctors() {
        return ResponseEntity.ok(
                userRepository.findByRoleAndApprovalStatus(Role.DENTIST, ApprovalStatus.PENDING)
        );
    }

    // Duyệt bác sĩ
    @PostMapping("/approve-doctor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveDoctor(@PathVariable Long id) {
        User doctor = userRepository.findById(id).orElseThrow();
        doctor.setApprovalStatus(ApprovalStatus.APPROVED);
        userRepository.save(doctor);
        return ResponseEntity.ok("Bác sĩ đã được duyệt.");
    }

    //  Từ chối bác sĩ
    @PostMapping("/reject-doctor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectDoctor(@PathVariable Long id) {
        User doctor = userRepository.findById(id).orElseThrow();
        doctor.setApprovalStatus(ApprovalStatus.REJECTED);
        userRepository.save(doctor);
        return ResponseEntity.ok("Bác sĩ đã bị từ chối.");
    }
}
