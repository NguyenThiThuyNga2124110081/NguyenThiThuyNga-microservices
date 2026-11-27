package com.example.appointmentservice.dto;

import java.time.LocalDateTime;

public record AppointmentFullDetailsResponse(
    Long id,
    Long patientId,
    Long doctorId,
    Long doctorScheduleId,
    String doctorName,
    String department,
    LocalDateTime appointmentDateTime,
    String status,
    String reason,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    PatientDTO patient,
    DoctorResponse doctor,
    DoctorScheduleDTO schedule
) {}
