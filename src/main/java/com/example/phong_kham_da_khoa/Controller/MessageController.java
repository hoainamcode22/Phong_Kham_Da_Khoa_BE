package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.dto.MessageRequest;
import com.example.phong_kham_da_khoa.Model.Message;
import com.example.phong_kham_da_khoa.Service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Gửi tin nhắn
    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DENTIST')")
    public ResponseEntity<Message> send(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.sendMessage(request));
    }

    // Xem hội thoại với 1 user
    @GetMapping("/with/{userId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DENTIST')")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesWith(userId));
    }
}
