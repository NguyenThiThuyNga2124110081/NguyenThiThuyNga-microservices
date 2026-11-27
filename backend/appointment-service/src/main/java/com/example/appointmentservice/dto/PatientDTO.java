package com.example.appointmentservice.dto;

import java.time.LocalDate;

public record PatientDTO(
    Long id,
    String fullName,
    LocalDate dateOfBirth,
    String gender,
    String identityCardNumber,
    String phoneNumber,
    String email
) {}
