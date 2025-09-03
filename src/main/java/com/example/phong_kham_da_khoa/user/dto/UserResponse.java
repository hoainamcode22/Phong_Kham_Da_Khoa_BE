package com.example.phong_kham_da_khoa.user.dto;

import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.user.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    // null với user không phải bác sĩ
    private ApprovalStatus approvalStatus;
}
