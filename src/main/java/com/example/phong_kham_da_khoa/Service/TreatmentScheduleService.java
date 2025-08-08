package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.dto.TreatmentScheduleRequest;
import com.example.phong_kham_da_khoa.Model.*;
import com.example.phong_kham_da_khoa.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TreatmentScheduleService {

    private final TreatmentScheduleRepository treatmentScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    public void createSchedule(TreatmentScheduleRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email).orElseThrow();
        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow();
        Clinic clinic = clinicRepository.findById(request.getClinicId()).orElseThrow();

        TreatmentSchedule schedule = new TreatmentSchedule();
        schedule.setStartDate(request.getStartDate());
        schedule.setIntervalDays(request.getIntervalDays());
        schedule.setTotalSessions(request.getTotalSessions());
        schedule.setNotes(request.getNotes());
        schedule.setPatient(patient);
        schedule.setDoctor(doctor);
        schedule.setClinic(clinic);

        treatmentScheduleRepository.save(schedule);

        // Tạo các lịch hẹn tương ứng
        LocalDateTime time = request.getStartDate();
        for (int i = 0; i < request.getTotalSessions(); i++) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentTime(time);
            appointment.setRecurring(true);
            appointment.setNotes("Lịch điều trị #" + (i + 1));
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setClinic(clinic);

            appointmentRepository.save(appointment);

            time = time.plusDays(request.getIntervalDays());
        }
    }
}
