package com.example.phong_kham_da_khoa.chat.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class MessageRequest {
    @NotNull(message = "receiverId is required")
    private Long receiverId;

    @NotBlank(message = "content is required")
    private String content;
}
