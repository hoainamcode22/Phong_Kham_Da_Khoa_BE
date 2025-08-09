package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Model.Role;
import com.example.phong_kham_da_khoa.Repository.ClinicRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerDoctor(DoctorRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng.");
        }

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new RuntimeException("Phòng khám không tồn tại"));

        User doctor = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DENTIST) // Gán vai trò DENTIST
                .build();
        return userRepository.save(doctor);
    }
}
