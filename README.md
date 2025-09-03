# Phong Kham Da Khoa - Backend

## 📂 Cấu trúc thư mục


src/main/java/com/example/phong_kham_da_khoa
│
├── admin
│ ├── controller
│ │ └── AdminController.java
│ ├── dto
│ │ └── ApprovalRequest.java
│ └── service
│ ├── AdminService.java
│ └── AdminServiceImpl.java
│
├── appointment
│ ├── controller
│ │ └── AppointmentController.java
│ ├── dto
│ │ ├── AppointmentRequest.java
│ │ └── AppointmentResponse.java
│ ├── model
│ │ └── Appointment.java
│ ├── repository
│ │ └── AppointmentRepository.java
│ └── service
│ └── AppointmentService.java
│
├── auth
│ ├── controller
│ │ └── AuthController.java
│ ├── dto
│ │ ├── AuthResponse.java
│ │ ├── LoginRequest.java
│ │ └── RegisterRequest.java
│ └── service
│ └── AuthService.java
│
├── chat
│ ├── controller
│ │ └── MessageController.java
│ ├── dto
│ │ ├── MessageRequest.java
│ │ └── MessageResponse.java
│ ├── model
│ │ └── Message.java
│ ├── repository
│ │ └── MessageRepository.java
│ └── service
│ └── MessageService.java
│
├── clinic
│ ├── controller
│ │ └── ClinicController.java
│ ├── dto
│ │ ├── AvailableSlotRequest.java
│ │ └── ClinicRequest.java
│ ├── model
│ │ ├── Clinic.java
│ │ └── ApprovalStatus.java
│ ├── repository
│ │ └── ClinicRepository.java
│ ├── schedule
│ │ ├── controller
│ │ │ └── WorkScheduleController.java
│ │ ├── model
│ │ │ ├── AvailableSlot.java
│ │ │ └── SlotType.java
│ │ ├── repository
│ │ │ └── AvailableSlotRepository.java
│ │ └── service
│ │ └── WorkScheduleService.java
│ └── service
│ └── ClinicService.java
│
├── common
│ ├── dto
│ │ ├── ApiResponse.java
│ │ └── PageResponse.java
│ └── util
│ └── DateUtils.java
│
├── config
│ ├── OpenApiConfig.java
│ └── WebCorsConfig.java
│
├── doctor
│ ├── controller
│ │ └── DoctorController.java
│ ├── dto
│ │ └── DoctorRegisterRequest.java
│ └── service
│ └── DoctorService.java
│
├── record
│ ├── controller
│ │ └── ExaminationController.java
│ ├── dto
│ │ └── ExaminationResultRequest.java
│ ├── model
│ │ └── ExaminationResult.java
│ ├── repository
│ │ └── ExaminationResultRepository.java
│ └── service
│ └── ExaminationService.java
│
├── treatment
│ ├── controller
│ │ └── TreatmentScheduleController.java
│ ├── dto
│ │ └── TreatmentScheduleRequest.java
│ ├── model
│ │ └── TreatmentSchedule.java
│ ├── repository
│ │ └── TreatmentScheduleRepository.java
│ └── service
│ └── TreatmentScheduleService.java
│
└── user
├── controller
│ └── UserController.java
├── dto
│ └── UserResponse.java
├── model
│ ├── User.java
│ └── Role.java
└── repository
└── UserRepository.java
