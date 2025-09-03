package com.example.phong_kham_da_khoa.clinic.dto;

import com.example.phong_kham_da_khoa.clinic.schedule.model.SlotType;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import lombok.Getter; import lombok.Setter;

@Getter @Setter
public class AvailableSlotRequest {
    @NotNull private Long doctorId;
    @NotNull private Long clinicId;

    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    @NotNull private SlotType slotType; // EXAMINATION/TREATMENT
    // optional: override capacity (mặc định auto theo slotType)
    private Integer capacity;
}
