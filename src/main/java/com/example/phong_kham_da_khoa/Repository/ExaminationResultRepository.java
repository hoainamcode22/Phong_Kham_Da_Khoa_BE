package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.ExaminationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.phong_kham_da_khoa.Model.User;

import java.util.List;

public interface ExaminationResultRepository extends JpaRepository<ExaminationResult, Long>
{
    List<ExaminationResult> findByPatient(User patient);
}
