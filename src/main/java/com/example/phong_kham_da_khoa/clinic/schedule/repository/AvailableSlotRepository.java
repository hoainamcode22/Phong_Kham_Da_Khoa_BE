package com.example.phong_kham_da_khoa.clinic.schedule.repository;

import com.example.phong_kham_da_khoa.clinic.schedule.model.AvailableSlot;
import com.example.phong_kham_da_khoa.clinic.schedule.model.SlotType;
import com.example.phong_kham_da_khoa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Long> {

    Optional<AvailableSlot> findByIdAndDoctorId(Long id, Long doctorId);

    boolean existsByDoctor_IdAndSlotTypeAndStartTimeLessThanAndEndTimeGreaterThan(
            Long doctorId, SlotType slotType, LocalDateTime end, LocalDateTime start);

    @Query("""
           SELECT s
           FROM AvailableSlot s
           WHERE s.clinic.id = :clinicId
             AND s.startTime >= :from AND s.startTime < :to
             AND (:slotType IS NULL OR s.slotType = :slotType)
             AND s.bookedCount < s.capacity
           """)
    List<AvailableSlot> searchFree(@Param("clinicId") Long clinicId,
                                   @Param("from") LocalDateTime from,
                                   @Param("to") LocalDateTime to,
                                   @Param("slotType") SlotType slotType);

    @Query("""
           SELECT s
           FROM AvailableSlot s
           WHERE s.doctor = :doctor
             AND s.bookedCount < s.capacity
           """)
    List<AvailableSlot> findFreeByDoctor(@Param("doctor") User doctor);
}
