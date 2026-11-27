package com.example.doctorservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorScheduleRequest(
    @NotNull(message = "Mã bác sĩ không được để trống")
    Long doctorId,

    @NotNull(message = "Ngày khám không được để trống")
    @FutureOrPresent(message = "Ngày khám phải là hôm nay hoặc trong tương lai")
    LocalDate workDate,

    String shift,
    LocalTime startTime,
    LocalTime endTime,
    Integer maxPatients,
    String roomNumber
) {}
