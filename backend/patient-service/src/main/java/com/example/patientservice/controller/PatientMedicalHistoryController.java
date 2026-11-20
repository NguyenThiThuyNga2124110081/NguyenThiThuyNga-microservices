package com.example.patientservice.controller;

import com.example.patientservice.entity.PatientMedicalHistory;
import com.example.patientservice.service.PatientMedicalHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-medical-histories")
public class PatientMedicalHistoryController {

    private final PatientMedicalHistoryService patientMedicalHistoryService;

    public PatientMedicalHistoryController(PatientMedicalHistoryService patientMedicalHistoryService) {
        this.patientMedicalHistoryService = patientMedicalHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<PatientMedicalHistory>> getAll() {
        return ResponseEntity.ok(patientMedicalHistoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientMedicalHistory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientMedicalHistoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PatientMedicalHistory> create(@Valid @RequestBody PatientMedicalHistory history) {
        PatientMedicalHistory created = patientMedicalHistoryService.create(history);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientMedicalHistory> update(@PathVariable Long id, @Valid @RequestBody PatientMedicalHistory history) {
        return ResponseEntity.ok(patientMedicalHistoryService.update(id, history));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientMedicalHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
