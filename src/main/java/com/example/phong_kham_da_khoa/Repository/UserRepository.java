package com.example.phong_kham_da_khoa.Repository;

import com.example.phong_kham_da_khoa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
