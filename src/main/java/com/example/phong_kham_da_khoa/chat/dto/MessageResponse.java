package com.example.phong_kham_da_khoa.chat.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MessageResponse {
    private Long id;
    private String content;
    private LocalDateTime timestamp;

    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
}
