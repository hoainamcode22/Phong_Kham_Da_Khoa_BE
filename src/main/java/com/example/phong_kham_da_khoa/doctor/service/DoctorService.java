package com.example.phong_kham_da_khoa.doctor.service;

import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import com.example.phong_kham_da_khoa.doctor.dto.DoctorRegisterRequest;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Owner tạo tài khoản bác sĩ cho phòng khám của mình.
     * Bác sĩ mới tạo sẽ có trạng thái PENDING để chờ Admin duyệt.
     */
    @Transactional
    public User registerDoctor(DoctorRegisterRequest request) {
        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng.");
        }

        // Kiểm tra phòng khám tồn tại (có thể đang PENDING hoặc APPROVED tuỳ flow)
        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new RuntimeException("Phòng khám không tồn tại"));

        // Tạo user role DENTIST, đặt trạng thái PENDING để chờ Admin duyệt
        User doctor = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DENTIST)
                .approvalStatus(ApprovalStatus.PENDING)
                .build();

        // Nếu User của bạn có liên kết trực tiếp tới Clinic (vd: doctor.setClinic(clinic);) thì mở dòng dưới:
        // doctor.setClinic(clinic);

        return userRepository.save(doctor);
    }
}
