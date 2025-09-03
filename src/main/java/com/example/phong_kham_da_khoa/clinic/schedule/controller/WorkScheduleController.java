package com.example.phong_kham_da_khoa.clinic.schedule.controller;

import com.example.phong_kham_da_khoa.clinic.dto.AvailableSlotRequest;
import com.example.phong_kham_da_khoa.clinic.schedule.model.AvailableSlot;
import com.example.phong_kham_da_khoa.clinic.schedule.model.SlotType;
import com.example.phong_kham_da_khoa.clinic.schedule.service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class WorkScheduleController {

    private final WorkScheduleService scheduleService;

    // Back-compat: route cũ
    @PostMapping("/add-slot")
    @PreAuthorize("hasAnyRole('DENTIST','CLINIC_OWNER')")
    public ResponseEntity<AvailableSlot> addSlotLegacy(@Valid @RequestBody AvailableSlotRequest request) {
        return ResponseEntity.ok(scheduleService.addSlot(request));
    }

    // RESTful mới
    @PostMapping("/slots")
    @PreAuthorize("hasAnyRole('DENTIST','CLINIC_OWNER')")
    public ResponseEntity<AvailableSlot> addSlot(@Valid @RequestBody AvailableSlotRequest request) {
        return ResponseEntity.ok(scheduleService.addSlot(request));
    }

    // Public query slot trống theo ngày & type
    @GetMapping("/clinics/{clinicId}/available")
    public ResponseEntity<List<AvailableSlot>> listAvailable(
            @PathVariable Long clinicId,
            @RequestParam LocalDate date,
            @RequestParam SlotType slotType) {
        return ResponseEntity.ok(scheduleService.findAvailable(clinicId, date, slotType));
    }
}
