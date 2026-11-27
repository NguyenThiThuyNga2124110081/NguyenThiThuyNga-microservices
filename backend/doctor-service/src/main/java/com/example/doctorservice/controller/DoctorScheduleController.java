package com.example.doctorservice.controller;

import com.example.doctorservice.dto.DoctorScheduleRequest;
import com.example.doctorservice.dto.DoctorScheduleResponse;
import com.example.doctorservice.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-schedules")
@CrossOrigin(origins = "*")
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    public DoctorScheduleController(DoctorScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorScheduleResponse>> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(scheduleService.getByWorkDate(date));
        }
        return ResponseEntity.ok(scheduleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorScheduleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getById(id));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorScheduleResponse>> getByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(scheduleService.getByDoctorId(doctorId));
    }

    @PostMapping
    public ResponseEntity<DoctorScheduleResponse> create(@Valid @RequestBody DoctorScheduleRequest request) {
        DoctorScheduleResponse created = scheduleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<DoctorScheduleResponse> bookSlot(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.bookSlot(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
