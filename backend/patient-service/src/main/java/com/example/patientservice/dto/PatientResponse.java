package com.example.patientservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
    Long id,
    String fullName,
    LocalDate dateOfBirth,
    String gender,
    String identityCardNumber,
    String phoneNumber,
    String email,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
