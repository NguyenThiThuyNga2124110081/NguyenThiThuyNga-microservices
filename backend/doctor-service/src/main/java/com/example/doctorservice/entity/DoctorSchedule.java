package com.example.doctorservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_schedules")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Mã bác sĩ không được để trống")
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @NotNull(message = "Ngày khám không được để trống")
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "shift", length = 20)
    private String shift; // SANG, CHIEU, TOI

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "max_patients", nullable = false)
    private Integer maxPatients = 20;

    @Column(name = "booked_patients", nullable = false)
    private Integer bookedPatients = 0;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "AVAILABLE"; // AVAILABLE, FULL, CANCELLED

    @Column(name = "room_number", length = 50)
    private String roomNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public DoctorSchedule() {
    }

    public DoctorSchedule(Long id, Long doctorId, LocalDate workDate, String shift, LocalTime startTime, LocalTime endTime, Integer maxPatients, Integer bookedPatients, String status, String roomNumber) {
        this.id = id;
        this.doctorId = doctorId;
        this.workDate = workDate;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxPatients = (maxPatients != null) ? maxPatients : 20;
        this.bookedPatients = (bookedPatients != null) ? bookedPatients : 0;
        this.status = (status != null) ? status : "AVAILABLE";
        this.roomNumber = roomNumber;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.maxPatients == null) this.maxPatients = 20;
        if (this.bookedPatients == null) this.bookedPatients = 0;
        if (this.status == null) this.status = "AVAILABLE";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(Integer maxPatients) {
        this.maxPatients = maxPatients;
    }

    public Integer getBookedPatients() {
        return bookedPatients;
    }

    public void setBookedPatients(Integer bookedPatients) {
        this.bookedPatients = bookedPatients;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
