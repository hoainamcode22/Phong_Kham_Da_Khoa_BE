package com.example.phong_kham_da_khoa.record.service;

import com.example.phong_kham_da_khoa.appointment.model.Appointment;
import com.example.phong_kham_da_khoa.appointment.repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.record.dto.ExaminationResultRequest;
import com.example.phong_kham_da_khoa.record.model.ExaminationResult;
import com.example.phong_kham_da_khoa.record.repository.ExaminationResultRepository;
import com.example.phong_kham_da_khoa.user.model.Role;
import com.example.phong_kham_da_khoa.user.model.User;
import com.example.phong_kham_da_khoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final AppointmentRepository appointmentRepository;
    private final ExaminationResultRepository examinationResultRepository;
    private final UserRepository userRepository;

    // Bác sĩ ghi kết quả
    public ExaminationResult submitResult(ExaminationResultRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User doctor = userRepository.findByEmail(email).orElseThrow();

        if (doctor.getRole() != Role.DENTIST) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ bác sĩ mới được nhập kết quả");
        }

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy lịch khám"));

        // Chỉ cho bác sĩ của appointment đó ghi kết quả
        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không phải bác sĩ của lịch khám này");
        }

        // Tránh ghi trùng cho cùng 1 appointment
        if (examinationResultRepository.existsByAppointment(appointment)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment này đã có kết quả");
        }

        User patient = appointment.getPatient();

        ExaminationResult result = new ExaminationResult();
        result.setAppointment(appointment);
        result.setResultText(request.getResultText());
        result.setDoctor(doctor);
        result.setPatient(patient);

        return examinationResultRepository.save(result);
    }

    // Bệnh nhân xem tất cả kết quả của chính mình
    public List<ExaminationResult> getMyResults() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userRepository.findByEmail(email).orElseThrow();
        if (me.getRole() != Role.CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ bệnh nhân mới xem được mục này");
        }
        return examinationResultRepository.findByPatient(me);
    }

    // Lấy kết quả theo appointmentId (bác sĩ hoặc bệnh nhân liên quan mới được xem)
    public ExaminationResult getByAppointment(Long appointmentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userRepository.findByEmail(email).orElseThrow();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy lịch khám"));

        // chỉ cho xem nếu là bác sĩ của ca khám hoặc bệnh nhân của ca khám
        boolean allowed =
                (me.getId().equals(appointment.getDoctor().getId())) ||
                        (me.getId().equals(appointment.getPatient().getId()));
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Không có quyền xem kết quả này");
        }

        return examinationResultRepository.findByAppointment(appointment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có kết quả cho appointment này"));
    }
}
