package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long>
{
}
