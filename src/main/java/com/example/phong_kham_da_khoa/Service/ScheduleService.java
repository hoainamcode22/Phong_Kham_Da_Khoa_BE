package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.AvailableSlot;
import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.AvailableSlotRepository;
import com.example.phong_kham_da_khoa.Repository.ClinicRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import com.example.phong_kham_da_khoa.dto.AvailableSlotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final AvailableSlotRepository availableSlotRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    public AvailableSlot addSlot(AvailableSlotRequest request) {
        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow();
        Clinic clinic = clinicRepository.findById(request.getClinicId()).orElseThrow();

        AvailableSlot slot = new AvailableSlot();
        slot.setDoctor(doctor);
        slot.setClinic(clinic);
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);

        return availableSlotRepository.save(slot);
    }
}