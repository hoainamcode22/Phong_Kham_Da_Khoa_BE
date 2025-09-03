package com.example.phong_kham_da_khoa.treatment.service;

import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.appointment.repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.clinic.model.ApprovalStatus;
import com.example.phong_kham_da_khoa.clinic.model.Clinic;
import com.example.phong_kham_da_khoa.clinic.repository.ClinicRepository;
import com.example.phong_kham_da_khoa.treatment.dto.TreatmentScheduleRequest;
import com.example.phong_kham_da_khoa.treatment.model.TreatmentSchedule;
import com.example.phong_kham_da_khoa.treatment.repository.TreatmentScheduleRepository;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentScheduleService {

    private final TreatmentScheduleRepository treatmentScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    public TreatmentSchedule createTreatmentSchedule(TreatmentScheduleRequest request) {
        // Lấy patient từ JWT
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (patient.getRole() != Role.CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ khách hàng mới tạo lịch điều trị");
        }

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bác sĩ"));
        if (doctor.getRole() != Role.DENTIST) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "doctorId không phải bác sĩ");
        }

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy phòng khám"));
        if (clinic.getStatus() != ApprovalStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phòng khám chưa được duyệt");
        }

        if (request.getTotalSessions() < 1 || request.getIntervalDays() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "totalSessions/intervalDays không hợp lệ");
        }
        if (request.getStartDate() == null || request.getStartDate().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startDate phải ở hiện tại hoặc tương lai");
        }

        // Tạo record lịch điều trị
        TreatmentSchedule schedule = new TreatmentSchedule();
        schedule.setStartDate(request.getStartDate());
        schedule.setIntervalDays(request.getIntervalDays());
        schedule.setTotalSessions(request.getTotalSessions());
        schedule.setNotes(request.getNotes());
        schedule.setPatient(patient);
        schedule.setDoctor(doctor);
        schedule.setClinic(clinic);
        schedule = treatmentScheduleRepository.save(schedule);

        // Tự động tạo các Appointment theo chu kỳ
        LocalDateTime time = request.getStartDate();
        for (int i = 0; i < request.getTotalSessions(); i++) {
            // Ngăn trùng lịch cho cả bác sĩ và bệnh nhân
            if (appointmentRepository.existsByDoctorAndAppointmentTime(doctor, time)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Bác sĩ đã có lịch vào " + time);
            }
            if (appointmentRepository.existsByPatientAndAppointmentTime(patient, time)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Bạn đã có lịch vào " + time);
            }

            Appointment ap = new Appointment();
            ap.setAppointmentTime(time);
            ap.setRecurring(true);
            ap.setNotes("Lịch điều trị #" + (i + 1) + (request.getNotes() != null ? (" - " + request.getNotes()) : ""));
            ap.setDoctor(doctor);
            ap.setPatient(patient);
            ap.setClinic(clinic);
            appointmentRepository.save(ap);

            time = time.plusDays(request.getIntervalDays());
        }
        return schedule;
    }

    @Transactional(readOnly = true)
    public List<TreatmentSchedule> getMyTreatmentSchedules() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (me.getRole() != Role.CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return treatmentScheduleRepository.findByPatient(me);
    }
}
