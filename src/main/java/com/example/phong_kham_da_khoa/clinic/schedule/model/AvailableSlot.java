package com.example.phong_kham_da_khoa.clinic.schedule.model;

import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "available_slot",
        uniqueConstraints = @UniqueConstraint(name = "uq_slot_clinic_doctor_start_type",
                columnNames = {"clinic_id","doctor_id","start_time","slot_type"}),
        indexes = {
                @Index(name = "idx_slot_start_time", columnList = "start_time"),
                @Index(name = "idx_slot_clinic_start", columnList = "clinic_id,start_time")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class AvailableSlot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", nullable = false)
    private SlotType slotType; // EXAMINATION | TREATMENT

    @Column(nullable = false)
    private int capacity;     // default: EXAMINATION=3, TREATMENT=1

    @Column(name = "booked_count", nullable = false)
    private int bookedCount;  // sẽ tăng khi khách đặt lịch

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_slot_doctor"))
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "fk_slot_clinic"))
    private Clinic clinic;

    @PrePersist
    public void prePersist() {
        if (capacity <= 0) {
            this.capacity = (slotType == SlotType.EXAMINATION ? 3 : 1);
        }
        if (bookedCount < 0) bookedCount = 0;
    }
}
