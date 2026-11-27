package com.example.doctorservice.controller;

import com.example.doctorservice.dto.DoctorRequest;
import com.example.doctorservice.dto.DoctorResponse;
import com.example.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAll(@RequestParam(required = false) String specialty) {
        if (specialty != null && !specialty.isBlank()) {
            return ResponseEntity.ok(doctorService.getBySpecialty(specialty));
        }
        return ResponseEntity.ok(doctorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> create(@Valid @RequestBody DoctorRequest request) {
        DoctorResponse created = doctorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable Long id, @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
