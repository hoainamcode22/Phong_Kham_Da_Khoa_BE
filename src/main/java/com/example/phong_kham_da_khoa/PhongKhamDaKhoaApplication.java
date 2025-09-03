package com.example.phong_kham_da_khoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "com.example.phong_kham_da_khoa")
@EnableJpaRepositories(basePackages = "com.example.phong_kham_da_khoa")
public class PhongKhamDaKhoaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhongKhamDaKhoaApplication.class, args);
	}
}
