package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.dto.TreatmentScheduleRequest;
import com.example.phong_kham_da_khoa.Service.TreatmentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treatment-schedules")
@RequiredArgsConstructor
public class TreatmentScheduleController {

    private final TreatmentScheduleService treatmentScheduleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> createSchedule(@RequestBody TreatmentScheduleRequest request) {
        treatmentScheduleService.createSchedule(request);
        return ResponseEntity.ok("Lịch điều trị định kỳ đã được tạo.");
    }
}
