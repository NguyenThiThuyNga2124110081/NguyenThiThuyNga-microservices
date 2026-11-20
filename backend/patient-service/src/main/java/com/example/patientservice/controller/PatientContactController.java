package com.example.patientservice.controller;

import com.example.patientservice.entity.PatientContact;
import com.example.patientservice.service.PatientContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-contacts")
public class PatientContactController {

    private final PatientContactService patientContactService;

    public PatientContactController(PatientContactService patientContactService) {
        this.patientContactService = patientContactService;
    }

    @GetMapping
    public ResponseEntity<List<PatientContact>> getAll() {
        return ResponseEntity.ok(patientContactService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientContact> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientContactService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PatientContact> create(@Valid @RequestBody PatientContact contact) {
        PatientContact created = patientContactService.create(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientContact> update(@PathVariable Long id, @Valid @RequestBody PatientContact contact) {
        return ResponseEntity.ok(patientContactService.update(id, contact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientContactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
