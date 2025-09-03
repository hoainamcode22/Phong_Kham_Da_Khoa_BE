package com.example.phong_kham_da_khoa.clinic.service;

import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Transactional
    public Clinic createClinic(String name, String address, String phone,
                               String openTime, String closeTime, User owner) {
        if (owner == null || owner.getId() == null) {
            throw new IllegalStateException("Không xác định được chủ phòng khám hiện tại");
        }

        // validate nhẹ
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Tên phòng khám không được trống");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Địa chỉ không được trống");
        if (phone == null || phone.isBlank()) throw new IllegalArgumentException("Số điện thoại không được trống");

        Clinic clinic = Clinic.builder()
                .name(name.trim())
                .address(address.trim())
                .phone(phone.trim())
                .openTime(openTime == null ? null : openTime.trim())
                .closeTime(closeTime == null ? null : closeTime.trim())
                .status(ApprovalStatus.PENDING)
                .owner(owner)
                .build();

        return clinicRepository.save(clinic);
    }

    @Transactional(readOnly = true)
    public List<Clinic> getAllPendingClinics() {
        return clinicRepository.findByStatus(ApprovalStatus.PENDING);
    }

    @Transactional
    public Clinic approveClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám"));
        if (clinic.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Chỉ được duyệt từ trạng thái PENDING");
        }
        clinic.setStatus(ApprovalStatus.APPROVED);
        return clinicRepository.save(clinic);
    }

    // Pagination: phòng khám đã được duyệt
    @Transactional(readOnly = true)
    public Page<Clinic> getApprovedClinics(Pageable pageable) {
        return clinicRepository.findByStatus(ApprovalStatus.APPROVED, pageable);
    }

    @Transactional
    public Clinic rejectClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng khám"));
        if (clinic.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Chỉ được từ chối từ trạng thái PENDING");
        }
        clinic.setStatus(ApprovalStatus.REJECTED);
        return clinicRepository.save(clinic);
    }
}
