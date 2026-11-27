package com.example.doctorservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record DoctorRequest(
    @NotBlank(message = "Tên bác sĩ không được để trống")
    @Size(max = 100, message = "Tên bác sĩ tối đa 100 ký tự")
    String fullName,

    @NotBlank(message = "Chuyên khoa không được để trống")
    @Size(max = 100, message = "Chuyên khoa tối đa 100 ký tự")
    String specialty,

    String phoneNumber,

    @Email(message = "Email không đúng định dạng")
    String email,

    BigDecimal consultationFee,

    Boolean isActive
) {}
