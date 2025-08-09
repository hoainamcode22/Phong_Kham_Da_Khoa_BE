package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.AvailableSlot;
import com.example.phong_kham_da_khoa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Long> {
    List<AvailableSlot> findByDoctorAndIsBookedFalse(User doctor);
}
