package com.example.doctorservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DoctorScheduleResponse(
    Long id,
    Long doctorId,
    LocalDate workDate,
    String shift,
    LocalTime startTime,
    LocalTime endTime,
    Integer maxPatients,
    Integer bookedPatients,
    String status,
    String roomNumber,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
