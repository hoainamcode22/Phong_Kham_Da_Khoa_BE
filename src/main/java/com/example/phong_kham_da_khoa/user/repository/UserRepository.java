package com.example.phong_kham_da_khoa.user.repository;

import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Lấy danh sách bác sĩ đang chờ duyệt
    List<User> findByRoleAndApprovalStatus(Role role, ApprovalStatus approvalStatus);
}
