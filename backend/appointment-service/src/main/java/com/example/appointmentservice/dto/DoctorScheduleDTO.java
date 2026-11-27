package com.example.appointmentservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorScheduleDTO(
    Long id,
    Long doctorId,
    LocalDate workDate,
    String shift,
    LocalTime startTime,
    LocalTime endTime,
    Integer maxPatients,
    Integer bookedPatients,
    String status,
    String roomNumber
) {}
