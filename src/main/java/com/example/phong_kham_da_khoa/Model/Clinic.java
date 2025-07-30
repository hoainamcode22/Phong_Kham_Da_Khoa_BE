package com.example.phong_kham_da_khoa.Model;

import com.example.phong_kham_da_khoa.Model.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String openTime;   // Giờ mở cửa, ví dụ "08:00"
    private String closeTime;  // Giờ đóng cửa, ví dụ "17:00"

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
}
