package com.example.appointmentservice.service;

import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.exception.ResourceNotFoundException;
import com.example.appointmentservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment create(Appointment appointment) {
        if (appointment.getPatientId() == null) {
            throw new IllegalArgumentException("Patient ID is required");
        }
        if (appointment.getAppointmentDateTime() == null) {
            throw new IllegalArgumentException("Appointment date and time is required");
        }
        if (appointment.getStatus() == null || appointment.getStatus().isBlank()) {
            appointment.setStatus("PENDING");
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment details) {
        Appointment existing = getById(id);

        if (details.getPatientId() != null) {
            existing.setPatientId(details.getPatientId());
        }
        if (details.getDoctorName() != null && !details.getDoctorName().isBlank()) {
            existing.setDoctorName(details.getDoctorName());
        }
        if (details.getDepartment() != null) {
            existing.setDepartment(details.getDepartment());
        }
        if (details.getAppointmentDateTime() != null) {
            existing.setAppointmentDateTime(details.getAppointmentDateTime());
        }
        if (details.getStatus() != null && !details.getStatus().isBlank()) {
            existing.setStatus(details.getStatus());
        }
        if (details.getReason() != null) {
            existing.setReason(details.getReason());
        }

        return appointmentRepository.save(existing);
    }

    public void delete(Long id) {
        Appointment existing = getById(id);
        appointmentRepository.delete(existing);
    }
}
