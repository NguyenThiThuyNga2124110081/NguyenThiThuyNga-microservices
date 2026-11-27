package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appointment_details")
public class AppointmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Appointment is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "symptoms", length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String symptoms;

    @Column(name = "consultation_type", length = 50, columnDefinition = "NVARCHAR(50)")
    private String consultationType;

    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;

    @Column(name = "fee")
    private Double fee;

    public AppointmentDetail() {
    }

    public AppointmentDetail(Long id, Appointment appointment, String symptoms, String consultationType, Integer estimatedDurationMinutes, Double fee) {
        this.id = id;
        this.appointment = appointment;
        this.symptoms = symptoms;
        this.consultationType = consultationType;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }

    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
