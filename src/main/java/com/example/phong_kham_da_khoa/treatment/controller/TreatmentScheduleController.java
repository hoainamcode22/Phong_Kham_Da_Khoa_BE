package com.example.phong_kham_da_khoa.treatment.controller;

import com.example.phong_kham_da_khoa.treatment.dto.TreatmentScheduleRequest;
import com.example.phong_kham_da_khoa.treatment.model.TreatmentSchedule;
import com.example.phong_kham_da_khoa.treatment.service.TreatmentScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatment-schedules")
@RequiredArgsConstructor
public class TreatmentScheduleController {

    private final TreatmentScheduleService treatmentScheduleService;

    // Khách hàng tạo lịch điều trị định kỳ (VD: mỗi 30 ngày, tổng 12 buổi)
    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> createTreatmentSchedule(@Valid @RequestBody TreatmentScheduleRequest request) {
        TreatmentSchedule ts = treatmentScheduleService.createTreatmentSchedule(request);
        String msg = "Lịch điều trị định kỳ đã được tạo: id=" + ts.getId()
                + ", totalSessions=" + ts.getTotalSessions()
                + ", intervalDays=" + ts.getIntervalDays();
        return ResponseEntity.ok(msg);
    }

    // Khách hàng xem tất cả lịch điều trị của chính mình
    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TreatmentSchedule>> myTreatmentSchedules() {
        return ResponseEntity.ok(treatmentScheduleService.getMyTreatmentSchedules());
    }
}
