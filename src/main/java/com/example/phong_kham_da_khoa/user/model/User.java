package com.example.phong_kham_da_khoa.user.model;



import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;


    //json này làm ẩn password
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Hoang Hà: Trạng thái duyệt áp dụng với bác sĩ
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @PrePersist
    public void prePersist() {
        if (role == Role.DENTIST && approvalStatus == null) {
            approvalStatus = ApprovalStatus.PENDING;
        }
    }
}
