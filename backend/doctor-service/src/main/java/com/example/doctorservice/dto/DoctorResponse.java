package com.example.doctorservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DoctorResponse(
    Long id,
    String fullName,
    String specialty,
    String phoneNumber,
    String email,
    BigDecimal consultationFee,
    Boolean isActive,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
