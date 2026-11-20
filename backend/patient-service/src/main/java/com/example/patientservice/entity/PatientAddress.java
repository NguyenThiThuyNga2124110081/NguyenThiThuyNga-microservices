package com.example.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient_addresses")
public class PatientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"contacts", "addresses", "medicalHistories"})
    private Patient patient;

    @Transient
    private Long patientId;

    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    @Size(max = 255, message = "Địa chỉ không vượt quá 255 ký tự")
    @Column(name = "address_line", nullable = false, length = 255)
    private String addressLine;

    @Size(max = 100, message = "Phường/Xã không vượt quá 100 ký tự")
    @Column(name = "ward", length = 100)
    private String ward;

    @Size(max = 100, message = "Quận/Huyện không vượt quá 100 ký tự")
    @Column(name = "district", length = 100)
    private String district;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    @Size(max = 100, message = "Tỉnh/Thành phố không vượt quá 100 ký tự")
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    public PatientAddress() {
    }

    public PatientAddress(Long id, Patient patient, String addressLine, String ward, String district, String city, Boolean isDefault) {
        this.id = id;
        this.patient = patient;
        this.addressLine = addressLine;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getPatientId() {
        if (patientId != null) {
            return patientId;
        }
        if (patient != null) {
            return patient.getId();
        }
        return null;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
