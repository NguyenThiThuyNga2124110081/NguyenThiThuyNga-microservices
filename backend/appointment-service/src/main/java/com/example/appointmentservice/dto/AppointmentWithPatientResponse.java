package com.example.appointmentservice.dto;

import java.time.LocalDateTime;

public record AppointmentWithPatientResponse(
    Long id,
    Long patientId,
    String doctorName,
    String department,
    LocalDateTime appointmentDateTime,
    String status,
    String reason,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    PatientDTO patient
) {}
