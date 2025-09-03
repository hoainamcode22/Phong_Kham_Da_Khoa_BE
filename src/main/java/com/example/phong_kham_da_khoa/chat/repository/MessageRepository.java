package com.example.phong_kham_da_khoa.chat.repository;

import com.example.phong_kham_da_khoa.chat.model.Message;
import com.example.phong_kham_da_khoa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // 1 chiều
    List<Message> findBySenderAndReceiver(User sender, User receiver);

    // Lấy tất cả tin liên quan đến một user
    List<Message> findBySenderOrReceiver(User sender, User receiver);

    // Hai chiều giữa 2 user, sắp theo thời gian tăng dần — dùng cho /with/{userId}
    List<Message> findBySenderAndReceiverOrSenderAndReceiverOrderByTimestampAsc(
            User sender1, User receiver1, User sender2, User receiver2
    );
}
