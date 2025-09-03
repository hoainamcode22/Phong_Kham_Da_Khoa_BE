package com.example.phong_kham_da_khoa.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClinicRequest {

    @NotBlank(message = "Tên phòng khám không được để trống")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9+\\-() ]{8,20}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    // Tùy chọn: nếu có nhập phải đúng HH:mm
    @Pattern(regexp = "^$|^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "openTime phải dạng HH:mm")
    private String openTime;

    @Pattern(regexp = "^$|^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "closeTime phải dạng HH:mm")
    private String closeTime;

    private String description; // optional
    private Long ownerId;       // optional
}
