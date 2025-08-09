package com.example.phong_kham_da_khoa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiver(User sender, User receiver);

    List<Message> findBySenderOrReceiver(User sender, User receiver); // để lấy toàn bộ lịch sử hội thoại
}
