package com.example.phong_kham_da_khoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // bật lên để nhắc lịch hẹn
@SpringBootApplication
public class PhongKhamDaKhoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhongKhamDaKhoaApplication.class, args);

	}

}
