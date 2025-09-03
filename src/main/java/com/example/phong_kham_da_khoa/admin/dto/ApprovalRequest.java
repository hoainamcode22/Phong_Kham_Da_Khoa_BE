package com.example.phong_kham_da_khoa.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    @NotBlank(message = "Vui lòng nhập lý do từ chối")
    private String reason;
}
