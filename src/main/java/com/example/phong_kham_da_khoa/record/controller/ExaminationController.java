package com.example.phong_kham_da_khoa.record.controller;

import com.example.phong_kham_da_khoa.record.dto.ExaminationResultRequest;
import com.example.phong_kham_da_khoa.record.model.ExaminationResult;
import com.example.phong_kham_da_khoa.record.service.ExaminationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    // Bác sĩ gửi kết quả
    @PostMapping("/submit")
    @PreAuthorize("hasRole('DENTIST')")
    public ResponseEntity<ExaminationResult> submit(@Valid @RequestBody ExaminationResultRequest request) {
        return ResponseEntity.ok(examinationService.submitResult(request));
    }

    // Bệnh nhân xem kết quả của chính mình
    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<ExaminationResult>> myResults() {
        return ResponseEntity.ok(examinationService.getMyResults());
    }

    // Xem kết quả theo appointmentId (bác sĩ của ca đó hoặc bệnh nhân của ca đó)
    @GetMapping("/by-appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','DENTIST')")
    public ResponseEntity<ExaminationResult> getByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(examinationService.getByAppointment(appointmentId));
    }
}
