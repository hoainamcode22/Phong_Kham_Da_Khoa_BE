package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phong_kham_da_khoa.Model.ApprovalStatus; // Hoang Hà: import thêm
import com.example.phong_kham_da_khoa.Model.Role; // Hoang Hà: import thêm

import java.util.Optional;
import java.util.List; // Hoang Hà: import thêm

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Hoang Hà: Lấy danh sách bác sĩ đang chờ duyệt
    List<User> findByRoleAndApprovalStatus(Role Role, ApprovalStatus approvalStatus);
}
