package com.example.phong_kham_da_khoa.chat.service;

import com.example.phong_kham_da_khoa.chat.dto.MessageRequest;
import com.example.phong_kham_da_khoa.chat.dto.MessageResponse;
import com.example.phong_kham_da_khoa.chat.model.Message;
import com.example.phong_kham_da_khoa.chat.repository.MessageRepository;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private static MessageResponse toDto(Message m) {
        return MessageResponse.builder()
                .id(m.getId())
                .content(m.getContent())
                .timestamp(m.getTimestamp())
                .senderId(m.getSender().getId())
                .senderName(m.getSender().getName())
                .receiverId(m.getReceiver().getId())
                .receiverName(m.getReceiver().getName())
                .build();
    }

    public MessageResponse sendMessage(MessageRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findByEmail(email).orElseThrow();
        User receiver = userRepository.findById(request.getReceiverId()).orElseThrow();

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("Không thể gửi tin nhắn cho chính mình");
        }
        // cắt bớt để khớp cột length=2000
        String content = request.getContent().trim();
        if (content.length() > 2000) content = content.substring(0, 2000);

        Message message = new Message();
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        return toDto(messageRepository.save(message));
    }

    public List<MessageResponse> getMessagesWith(Long userId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow();
        User targetUser = userRepository.findById(userId).orElseThrow();

        return messageRepository
                .findBySenderAndReceiverOrSenderAndReceiverOrderByTimestampAsc(
                        currentUser, targetUser, targetUser, currentUser)
                .stream().map(MessageService::toDto).toList();
    }
}
