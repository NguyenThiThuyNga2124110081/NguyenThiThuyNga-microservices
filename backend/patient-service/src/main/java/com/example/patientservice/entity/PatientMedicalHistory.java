package com.example.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_medical_histories")
public class PatientMedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"contacts", "addresses", "medicalHistories"})
    private Patient patient;

    @Transient
    private Long patientId;

    @NotBlank(message = "Tên bệnh lý hoặc tiền sử bệnh không được để trống")
    @Size(max = 255, message = "Tên bệnh lý không vượt quá 255 ký tự")
    @Column(name = "condition_name", nullable = false, length = 255)
    private String conditionName;

    @Column(name = "diagnosed_date")
    private LocalDate diagnosedDate;

    @Column(name = "treatment", columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    public PatientMedicalHistory() {
    }

    public PatientMedicalHistory(Long id, Patient patient, String conditionName, LocalDate diagnosedDate, String treatment, String notes) {
        this.id = id;
        this.patient = patient;
        this.conditionName = conditionName;
        this.diagnosedDate = diagnosedDate;
        this.treatment = treatment;
        this.notes = notes;
    }

    @PrePersist
    protected void onCreate() {
        if (this.recordedAt == null) {
            this.recordedAt = LocalDateTime.now();
        }
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

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public LocalDate getDiagnosedDate() {
        return diagnosedDate;
    }

    public void setDiagnosedDate(LocalDate diagnosedDate) {
        this.diagnosedDate = diagnosedDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
