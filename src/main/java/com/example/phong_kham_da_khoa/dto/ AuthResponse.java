package com.example.phongkham_da_khoa;

public class AuthResponse {
    public String token;
    public String name;
    public Role role;

    public AuthResponse(String token, String name, Role role) {
        this.token = token;
        this.name = name;
        this.role = role;
    }
}
