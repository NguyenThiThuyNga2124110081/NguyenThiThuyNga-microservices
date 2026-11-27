package com.example.appointmentservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AppointmentRequest(
    @NotNull(message = "Mã bệnh nhân không được để trống")
    @Positive(message = "Mã bệnh nhân phải là số nguyên dương")
    Long patientId,

    Long doctorId,

    Long doctorScheduleId,

    String doctorName,

    String department,

    @NotNull(message = "Thời gian khám không được để trống")
    @Future(message = "Thời gian khám phải ở tương lai")
    LocalDateTime appointmentDateTime,

    String status,

    @Size(max = 255, message = "Lý do khám tối đa 255 ký tự")
    String reason
) {}
