package com.example.phong_kham_da_khoa.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long receiverId;
    private String content;
}
