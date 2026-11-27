package com.example.patientservice.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PatientRequest(
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên tối đa 100 ký tự")
    String fullName,

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    LocalDate dateOfBirth,

    @NotBlank(message = "Giới tính không được để trống")
    @Pattern(regexp = "^(Nam|Nữ|Nu|Khác|Khac)$", message = "Giới tính phải là Nam, Nữ hoặc Khác")
    String gender,

    @NotBlank(message = "CCCD không được để trống")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "CCCD/CMND phải gồm 9 đến 12 chữ số")
    String identityCardNumber,

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "Số điện thoại không đúng định dạng")
    String phoneNumber,

    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email tối đa 100 ký tự")
    String email
) {}
