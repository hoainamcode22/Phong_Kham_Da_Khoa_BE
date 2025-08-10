package com.example.phong_kham_da_khoa.Controller;


import com.example.phong_kham_da_khoa.dto.AvailableSlotRequest;
import com.example.phong_kham_da_khoa.Model.AvailableSlot;
import com.example.phong_kham_da_khoa.Service.ScheduleService;
import com.example.phong_kham_da_khoa.dto.AppointmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add-slot")
    @PreAuthorize("hasAnyRole('DENTIST', 'CLINIC_OWNER')")
    public ResponseEntity<AvailableSlot> addSlot(@RequestBody AvailableSlotRequest request) {
        return ResponseEntity.ok(scheduleService.addSlot(request));
    }
}
