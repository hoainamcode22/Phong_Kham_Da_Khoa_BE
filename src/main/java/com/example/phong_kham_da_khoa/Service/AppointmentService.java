package com.example.phong_kham_da_khoa.Service;

import com.example.phong_kham_da_khoa.dto.AppointmentRequest;
import com.example.phong_kham_da_khoa.dto.AppointmentResponse;
import com.example.phong_kham_da_khoa.Model.Appointment;
import com.example.phong_kham_da_khoa.Model.User;
import com.example.phong_kham_da_khoa.Model.Schedule; // Có thể cần Schedule để kiểm tra lịch trống
import com.example.phong_kham_da_khoa.Repository.AppointmentRepository;
import com.example.phong_kham_da_khoa.Repository.UserRepository; // Cần để tìm User theo ID
import com.example.phong_kham_da_khoa.Repository.ScheduleRepository; // Cần để tìm và cập nhật lịch trống
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository; // Inject UserRepository để tìm kiếm User (bệnh nhân/bác sĩ)
    private final ScheduleRepository scheduleRepository; // Inject ScheduleRepository
    private final NotificationService notificationService; // Sẽ inject sau khi tạo NotificationService

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              ScheduleRepository scheduleRepository,
                              NotificationService notificationService) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // 1. Kiểm tra sự tồn tại của bệnh nhân và bác sĩ
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + request.getPatientId()));
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + request.getDoctorId()));

        // TODO: Thêm kiểm tra vai trò (Role) của User (patient phải là PATIENT, doctor phải là DOCTOR)
        // if (!patient.getRole().getName().equals("PATIENT")) { throw new RuntimeException("User is not a patient"); }
        // if (!doctor.getRole().getName().equals("DOCTOR")) { throw new RuntimeException("User is not a doctor"); }


        // 2. Kiểm tra lịch trống của bác sĩ (dựa vào Schedule)
        LocalDate appointmentDate = request.getAppointmentTime().toLocalDate();
        LocalTime appointmentStartTime = request.getAppointmentTime().toLocalTime();
        // Giả định mỗi cuộc hẹn là 30 phút. Bạn có thể thay đổi logic này.
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);

        // Tìm lịch làm việc của bác sĩ trong ngày đó
        List<Schedule> doctorSchedules = scheduleRepository.findByDoctorAndScheduleDateAndIsAvailableTrue(doctor, appointmentDate);

        boolean isDoctorAvailable = false;
        Schedule targetSchedule = null;

        for (Schedule schedule : doctorSchedules) {
            // Kiểm tra xem thời gian cuộc hẹn có nằm trong khung giờ làm việc có sẵn của bác sĩ không
            if (!appointmentStartTime.isBefore(schedule.getStartTime()) &&
                    !appointmentEndTime.isAfter(schedule.getEndTime()) &&
                    schedule.isAvailable()) { // Đảm bảo khung giờ đó còn khả dụng
                isDoctorAvailable = true;
                targetSchedule = schedule; // Lưu lại Schedule nếu tìm thấy
                break;
            }
        }

        if (!isDoctorAvailable) {
            throw new RuntimeException("Doctor is not available at the requested time or no suitable schedule found.");
        }

        // TODO: Có thể cần chi tiết hơn về việc quản lý slot thời gian trong Schedule
        // ví dụ: chia Schedule thành các slot nhỏ hơn để tránh chồng lấn chính xác.
        // Hiện tại, logic đơn giản là kiểm tra xem cuộc hẹn có nằm gọn trong một schedule available không.

        // 3. Tạo cuộc hẹn mới
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING); // Mặc định là PENDING

        // 4. Lưu cuộc hẹn
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 5. Cập nhật trạng thái lịch làm việc của bác sĩ (nếu cần)
        // Nếu một Schedule chỉ đại diện cho một slot thời gian cụ thể (ví dụ 30p),
        // bạn có thể set isAvailable = false cho slot đó.
        // Nếu Schedule đại diện cho một khoảng thời gian dài, bạn cần logic phức tạp hơn
        // để "cắt" hoặc đánh dấu phần đã đặt.
        // Đối với ví dụ này, chúng ta giả định mỗi Schedule là một khung giờ đặt được.
        if (targetSchedule != null) {
            // targetSchedule.setAvailable(false); // Có thể cần thay đổi logic này nếu muốn quản lý slot chi tiết hơn
            // scheduleRepository.save(targetSchedule);
        }


        // 6. Gửi thông báo (mock)
        notificationService.sendAppointmentNotification(patient, doctor, savedAppointment);

        return new AppointmentResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        return appointmentRepository.findByPatient(patient).stream()
                .map(AppointmentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));
        return appointmentRepository.findByDoctor(doctor).stream()
                .map(AppointmentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        return new AppointmentResponse(appointment);
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long id, Appointment.AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        // TODO: Thêm logic kiểm tra hợp lệ của trạng thái chuyển đổi
        // Ví dụ: Không thể chuyển từ CANCELLED sang CONFIRMED.

        appointment.setStatus(newStatus);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Gửi thông báo về việc cập nhật trạng thái
        notificationService.sendAppointmentStatusUpdateNotification(updatedAppointment);

        return new AppointmentResponse(updatedAppointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
        // TODO: Có thể cần cập nhật lại Schedule nếu cuộc hẹn bị hủy
    }

    // Một phương thức để cập nhật một cuộc hẹn đầy đủ hơn (nếu cần)
    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + request.getPatientId()));
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + request.getDoctorId()));

        // TODO: Kiểm tra lại lịch trống của bác sĩ nếu thời gian hoặc bác sĩ thay đổi
        // và xử lý cập nhật Schedule nếu cần.

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        // Trạng thái không được cập nhật qua request này, chỉ qua updateAppointmentStatus

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return new AppointmentResponse(updatedAppointment);
    }
}