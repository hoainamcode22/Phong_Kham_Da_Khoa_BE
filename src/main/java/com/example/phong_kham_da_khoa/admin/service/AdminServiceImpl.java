package com.example.phong_kham_da_khoa.admin.service;

import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getPendingDoctors() {
        // Method này bạn đã có trong UserRepository
        return userRepository.findByRoleAndApprovalStatus(Role.DENTIST, ApprovalStatus.PENDING);
    }

    @Override
    public void approveDoctor(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bác sĩ"));
        if (doctor.getRole() != Role.DENTIST) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng không phải bác sĩ");
        }
        doctor.setApprovalStatus(ApprovalStatus.APPROVED);
        userRepository.save(doctor);
    }

    @Override
    public void rejectDoctor(Long doctorId, String reason) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bác sĩ"));
        if (doctor.getRole() != Role.DENTIST) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng không phải bác sĩ");
        }
        doctor.setApprovalStatus(ApprovalStatus.REJECTED);
        log.info("Reject doctor {} reason: {}", doctorId, reason);
        userRepository.save(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clinic> getPendingClinics() {
        // Lưu ý: dùng findByStatus vì entity Clinic dùng field 'status'
        return clinicRepository.findByStatus(ApprovalStatus.PENDING);
    }

    @Override
    public void approveClinic(Long clinicId) {
        Clinic c = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy phòng khám"));
        c.setStatus(ApprovalStatus.APPROVED);
        clinicRepository.save(c);
    }

    @Override
    public void rejectClinic(Long clinicId, String reason) {
        Clinic c = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy phòng khám"));
        c.setStatus(ApprovalStatus.REJECTED);
        log.info("Reject clinic {} reason: {}", clinicId, reason);
        clinicRepository.save(c);
    }
}
