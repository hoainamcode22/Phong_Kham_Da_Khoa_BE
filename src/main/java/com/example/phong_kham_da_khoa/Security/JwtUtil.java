package com.example.phong_kham_da_khoa.Security;

import com.example.phong_kham_da_khoa.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}") // default 24h
    private long expirationMs;

    private Key getSignKey() {
        byte[] keyBytes;
        try {
            // Ưu tiên secret dạng Base64
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException e) {
            // Fallback: secret dạng plain text
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        // Lưu ý: chuỗi secret cần đủ mạnh (>= 32 bytes) để HS256 hợp lệ
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(user.getEmail())                  // dùng email làm subject
                .claim("role", user.getRole().name())         // optional
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean isTokenValid(String token, User user) {
        String email = extractEmail(token);
        Date exp = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return email.equals(user.getEmail()) && exp.after(new Date());
    }
}
