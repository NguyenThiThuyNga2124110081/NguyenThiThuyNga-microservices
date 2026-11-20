package com.example.patientservice.controller;

import com.example.patientservice.entity.PatientAddress;
import com.example.patientservice.service.PatientAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-addresses")
public class PatientAddressController {

    private final PatientAddressService patientAddressService;

    public PatientAddressController(PatientAddressService patientAddressService) {
        this.patientAddressService = patientAddressService;
    }

    @GetMapping
    public ResponseEntity<List<PatientAddress>> getAll() {
        return ResponseEntity.ok(patientAddressService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientAddress> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientAddressService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PatientAddress> create(@Valid @RequestBody PatientAddress address) {
        PatientAddress created = patientAddressService.create(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientAddress> update(@PathVariable Long id, @Valid @RequestBody PatientAddress address) {
        return ResponseEntity.ok(patientAddressService.update(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
