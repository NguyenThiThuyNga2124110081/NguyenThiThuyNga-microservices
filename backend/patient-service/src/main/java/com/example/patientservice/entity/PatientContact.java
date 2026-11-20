package com.example.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient_contacts")
public class PatientContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"contacts", "addresses", "medicalHistories"})
    private Patient patient;

    @Transient
    private Long patientId;

    @NotBlank(message = "Tên người liên hệ không được để trống")
    @Size(max = 100, message = "Tên người liên hệ không vượt quá 100 ký tự")
    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    @NotBlank(message = "Mối quan hệ không được để trống")
    @Size(max = 50, message = "Mối quan hệ không vượt quá 50 ký tự")
    @Column(name = "relationship", nullable = false, length = 50)
    private String relationship;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 20, message = "Số điện thoại không vượt quá 20 ký tự")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100, message = "Email không vượt quá 100 ký tự")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "is_emergency")
    private Boolean isEmergency = false;

    public PatientContact() {
    }

    public PatientContact(Long id, Patient patient, String contactName, String relationship, String phoneNumber, String email, Boolean isEmergency) {
        this.id = id;
        this.patient = patient;
        this.contactName = contactName;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEmergency = isEmergency;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
}
