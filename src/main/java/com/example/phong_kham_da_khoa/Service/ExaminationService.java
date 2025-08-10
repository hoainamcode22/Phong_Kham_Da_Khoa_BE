package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.Model.*;
import com.example.phong_kham_da_khoa.Repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.Repository.ExaminationResultRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository;
import com.example.phong_kham_da_khoa.dto.ExaminationResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final AppointmentRepository appointmentRepository;
    private final ExaminationResultRepository examinationResultRepository;
    private final UserRepository userRepository;

    public ExaminationResult submitResult(ExaminationResultRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User doctor = userRepository.findByEmail(email).orElseThrow();

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId()).orElseThrow();
        User patient = appointment.getPatient();

        ExaminationResult result = new ExaminationResult();
        result.setAppointment(appointment);
        result.setResultText(request.getResultText());
        result.setDoctor(doctor);
        result.setPatient(patient);

        return examinationResultRepository.save(result);
    }
}
