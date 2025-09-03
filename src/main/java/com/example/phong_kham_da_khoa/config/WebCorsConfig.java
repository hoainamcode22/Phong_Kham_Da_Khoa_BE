package com.example.phong_kham_da_khoa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class WebCorsConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        CorsConfiguration cfg = new CorsConfiguration();
        // Origins FE dev: Vite & React
        cfg.setAllowedOrigins(List.of(
                "http://localhost:5174",
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));
        // Cho phép gửi cookie/Authorization header nếu cần
        cfg.setAllowCredentials(true);
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of(
                "Authorization", "Content-Type", "X-Requested-With",
                "X-Auth-Token", "Accept", "Origin"
        ));
        // Cho phép cache preflight
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return new CorsFilter(source);
    }
}
