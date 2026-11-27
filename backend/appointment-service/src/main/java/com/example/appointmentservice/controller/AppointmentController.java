package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentFullDetailsResponse;
import com.example.appointmentservice.dto.AppointmentRequest;
import com.example.appointmentservice.dto.AppointmentResponse;
import com.example.appointmentservice.dto.AppointmentWithPatientResponse;
import com.example.appointmentservice.dto.DoctorResponse;
import com.example.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    // API kiểm tra giao tiếp RestClient với Doctor Service (Lab 03 - Mục 6)
    @GetMapping("/doctor/{id}")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getDoctor(id));
    }

    @GetMapping("/{id}/with-patient")
    public ResponseEntity<AppointmentWithPatientResponse> getWithPatient(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getWithPatient(id));
    }

    @GetMapping("/{id}/full-details")
    public ResponseEntity<AppointmentFullDetailsResponse> getFullDetails(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getFullDetails(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> getByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse created = appointmentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
