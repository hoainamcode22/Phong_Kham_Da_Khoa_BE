package com.example.phong_kham_da_khoa.clinic.schedule.service;

import com.example.phong_kham_da_khoa.clinic.dto.AvailableSlotRequest;
import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import com.example.phong_kham_da_khoa.clinic.schedule.model.AvailableSlot;
import com.example.phong_kham_da_khoa.clinic.schedule.model.SlotType;
import com.example.phong_kham_da_khoa.clinic.schedule.repository.AvailableSlotRepository;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

    private final AvailableSlotRepository availableSlotRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    public AvailableSlot addSlot(AvailableSlotRequest req) {
        // validate doctor
        User doctor = userRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (doctor.getRole() != Role.DENTIST) {
            throw new IllegalArgumentException("User is not a DENTIST");
        }

        // validate clinic
        Clinic clinic = clinicRepository.findById(req.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        if (clinic.getStatus() != ApprovalStatus.APPROVED) {
            throw new IllegalStateException("Clinic is not APPROVED");
        }

        // validate time
        if (req.getStartTime() == null || req.getEndTime() == null
                || !req.getEndTime().isAfter(req.getStartTime())) {
            throw new IllegalArgumentException("Invalid time range");
        }

        // tránh trùng slot cùng bác sĩ & loại slot
        boolean overlap = availableSlotRepository
                .existsByDoctor_IdAndSlotTypeAndStartTimeLessThanAndEndTimeGreaterThan(
                        doctor.getId(), req.getSlotType(), req.getEndTime(), req.getStartTime());
        if (overlap) {
            throw new IllegalStateException("Overlapping slot exists");
        }

        // build slot
        AvailableSlot slot = new AvailableSlot();
        slot.setDoctor(doctor);
        slot.setClinic(clinic);
        slot.setStartTime(req.getStartTime());
        slot.setEndTime(req.getEndTime());
        slot.setSlotType(req.getSlotType());
        slot.setBookedCount(0);

        // capacity: mặc định theo type hoặc dùng override
        int capacity = (req.getCapacity() != null && req.getCapacity() > 0)
                ? req.getCapacity()
                : (req.getSlotType() == SlotType.EXAMINATION ? 3 : 1);
        slot.setCapacity(capacity);

        return availableSlotRepository.save(slot);
    }

    @Transactional(readOnly = true)
    public List<AvailableSlot> findAvailable(Long clinicId, LocalDate date, SlotType slotType) {
        // dùng [from, to) để query đơn giản hơn
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return availableSlotRepository.searchFree(clinicId, from, to, slotType);
    }
}
