package com.example.phong_kham_da_khoa.admin.service;

import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.user.model.User;

import java.util.List;

public interface AdminService {
    // Doctor
    List<User> getPendingDoctors();
    void approveDoctor(Long doctorId);
    void rejectDoctor(Long doctorId, String reason);

    //  Clinic – duyệt phòng khám ở đây
    List<Clinic> getPendingClinics();
    void approveClinic(Long clinicId);
    void rejectClinic(Long clinicId, String reason);
}
