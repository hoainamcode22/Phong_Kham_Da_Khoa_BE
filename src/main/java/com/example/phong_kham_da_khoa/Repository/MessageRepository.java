package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.Message;
import com.example.phong_kham_da_khoa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiver(User sender, User receiver);

    List<Message> findBySenderOrReceiver(User sender, User receiver); // để lấy toàn bộ lịch sử hội thoại
}
