package com.example.phong_kham_da_khoa.user.controller;

import com.example.phong_kham_da_khoa.user.dto.UserResponse;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private static UserResponse toDto(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .approvalStatus(u.getApprovalStatus())
                .build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(toDto(currentUser));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteMyAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        userRepository.deleteById(currentUser.getId());
        return ResponseEntity.ok("Account deleted");
    }
}
