package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Model.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByStatus(ApprovalStatus status);
}
