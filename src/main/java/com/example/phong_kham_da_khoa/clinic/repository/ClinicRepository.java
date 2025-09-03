package com.example.phong_kham_da_khoa.clinic.repository;

import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByStatus(ApprovalStatus status);
    List<Clinic> findByOwner_Id(Long ownerId);

    // Phân trang danh sách phòng khám theo trạng thái
    Page<Clinic> findByStatus(ApprovalStatus status, Pageable pageable);
}
