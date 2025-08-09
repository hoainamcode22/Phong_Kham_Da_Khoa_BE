package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.dto.ExaminationResultRequest;
import com.example.phong_kham_da_khoa.Model.ExaminationResult;
import com.example.phong_kham_da_khoa.Service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('DENTIST')")
    public ResponseEntity<ExaminationResult> submit(@RequestBody ExaminationResultRequest request) {
        return ResponseEntity.ok(examinationService.submitResult(request));
    }
}
