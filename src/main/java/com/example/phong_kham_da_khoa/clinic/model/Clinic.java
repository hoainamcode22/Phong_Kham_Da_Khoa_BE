package com.example.phong_kham_da_khoa.clinic.model;


import com.example.phong_kham_da_khoa.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "clinic",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_clinic_owner_name",
                        columnNames = {"owner_id", "name"}
                )
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "open_time", length = 10)  // "08:00"
    private String openTime;

    @Column(name = "close_time", length = 10) // "17:00"
    private String closeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private ApprovalStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "owner_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_clinic_owner")
    )
    private User owner;

    @PrePersist
    public void prePersist() {
        if (status == null) status = ApprovalStatus.PENDING;
    }
}
