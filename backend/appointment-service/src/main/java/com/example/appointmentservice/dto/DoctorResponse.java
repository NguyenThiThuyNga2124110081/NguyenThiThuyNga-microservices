package com.example.appointmentservice.dto;

import java.math.BigDecimal;

public record DoctorResponse(
    Long id,
    String fullName,
    String specialty,
    String phoneNumber,
    String email,
    BigDecimal consultationFee,
    Boolean isActive
) {}
