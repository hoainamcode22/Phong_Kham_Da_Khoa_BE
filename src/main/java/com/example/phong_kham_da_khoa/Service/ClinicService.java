package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.*;
import com.example.phong_kham_da_khoa.Repository.ClinicRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public Clinic createClinic(String name, String address, String phone, User owner) {
        Clinic clinic = Clinic.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .status(ApprovalStatus.PENDING)
                .owner(owner)
                .build();

        return clinicRepository.save(clinic);
    }

    public List<Clinic> getAllPendingClinics() {
        return clinicRepository.findByStatus(ApprovalStatus.PENDING);
    }

    public Clinic approveClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Kh tim thay Clinic"));

        clinic.setStatus(ApprovalStatus.APPROVED);
        return clinicRepository.save(clinic);
    }

    public Clinic rejectClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Kh tim thay Clinic"));

        clinic.setStatus(ApprovalStatus.REJECTED);
        return clinicRepository.save(clinic);
    }
}
