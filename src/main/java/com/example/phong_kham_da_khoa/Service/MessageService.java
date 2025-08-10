package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.Message;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Repository.MessageRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import com.example.phong_kham_da_khoa.dto.MessageRequest;
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

    public Message sendMessage(MessageRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findByEmail(email).orElseThrow();

        User receiver = userRepository.findById(request.getReceiverId()).orElseThrow();

        Message message = new Message();
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesWith(Long userId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow();
        User targetUser = userRepository.findById(userId).orElseThrow();

        return messageRepository.findBySenderAndReceiver(currentUser, targetUser);
    }
}
