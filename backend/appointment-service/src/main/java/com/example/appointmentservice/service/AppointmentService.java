package com.example.appointmentservice.service;

import com.example.appointmentservice.client.DoctorClient;
import com.example.appointmentservice.client.PatientClient;
import com.example.appointmentservice.dto.*;
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
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientClient patientClient,
                              DoctorClient doctorClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    public AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDoctorScheduleId(),
                appointment.getDoctorName(),
                appointment.getDepartment(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus(),
                appointment.getReason(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch hẹn với ID: " + id));
        return toResponse(appointment);
    }

    @Transactional(readOnly = true)
    public Appointment getEntityById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch hẹn với ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctor(Long doctorId) {
        return doctorClient.getById(doctorId);
    }

    @Transactional(readOnly = true)
    public AppointmentWithPatientResponse getWithPatient(Long id) {
        Appointment appointment = getEntityById(id);
        PatientDTO patient = patientClient.getById(appointment.getPatientId());
        return new AppointmentWithPatientResponse(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorName(),
                appointment.getDepartment(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus(),
                appointment.getReason(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt(),
                patient
        );
    }

    @Transactional(readOnly = true)
    public AppointmentFullDetailsResponse getFullDetails(Long id) {
        Appointment app = getEntityById(id);
        PatientDTO patient = null;
        try {
            patient = patientClient.getById(app.getPatientId());
        } catch (Exception ignored) {}

        DoctorResponse doctor = null;
        if (app.getDoctorId() != null) {
            try {
                doctor = doctorClient.getById(app.getDoctorId());
            } catch (Exception ignored) {}
        }

        DoctorScheduleDTO schedule = null;
        if (app.getDoctorScheduleId() != null) {
            try {
                schedule = doctorClient.getScheduleById(app.getDoctorScheduleId());
            } catch (Exception ignored) {}
        }

        return new AppointmentFullDetailsResponse(
                app.getId(),
                app.getPatientId(),
                app.getDoctorId(),
                app.getDoctorScheduleId(),
                app.getDoctorName(),
                app.getDepartment(),
                app.getAppointmentDateTime(),
                app.getStatus(),
                app.getReason(),
                app.getCreatedAt(),
                app.getUpdatedAt(),
                patient,
                doctor,
                schedule
        );
    }

    public AppointmentResponse create(AppointmentRequest request) {
        // 1. Kiểm tra thông tin bệnh nhân qua RestClient sang Patient Service
        patientClient.getById(request.patientId());

        String doctorName = request.doctorName();
        String department = request.department();

        // 2. Kiểm tra thông tin bác sĩ và lịch khám qua RestClient sang Doctor Service (Lab 03)
        if (request.doctorId() != null) {
            DoctorResponse doctor = doctorClient.getById(request.doctorId());
            if (!Boolean.TRUE.equals(doctor.isActive())) {
                throw new IllegalArgumentException("Bác sĩ '" + doctor.fullName() + "' hiện không hoạt động, không thể đặt lịch khám");
            }
            if (doctorName == null || doctorName.isBlank()) {
                doctorName = doctor.fullName();
            }
            if (department == null || department.isBlank()) {
                department = doctor.specialty();
            }
        }

        if (request.doctorScheduleId() != null) {
            DoctorScheduleDTO schedule = doctorClient.getScheduleById(request.doctorScheduleId());
            if (!"AVAILABLE".equalsIgnoreCase(schedule.status())) {
                throw new IllegalArgumentException("Lịch khám của bác sĩ không khả dụng (Trạng thái: " + schedule.status() + ")");
            }
            // Giữ chỗ lịch khám trên Doctor Service
            doctorClient.bookSlot(request.doctorScheduleId());
        }

        if (doctorName == null || doctorName.isBlank()) {
            doctorName = "Bác sĩ chuyên khoa";
        }
        if (department == null || department.isBlank()) {
            department = "Khoa Khám bệnh";
        }

        String status = (request.status() == null || request.status().isBlank()) ? "PENDING" : request.status();

        Appointment appointment = new Appointment(
                null,
                request.patientId(),
                request.doctorId(),
                request.doctorScheduleId(),
                doctorName,
                department,
                request.appointmentDateTime(),
                status,
                request.reason()
        );
        Appointment saved = appointmentRepository.save(appointment);
        return toResponse(saved);
    }

    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment existing = getEntityById(id);

        if (request.patientId() != null && !request.patientId().equals(existing.getPatientId())) {
            patientClient.getById(request.patientId());
            existing.setPatientId(request.patientId());
        }

        if (request.doctorId() != null) {
            DoctorResponse doctor = doctorClient.getById(request.doctorId());
            existing.setDoctorId(request.doctorId());
            existing.setDoctorName(doctor.fullName());
            existing.setDepartment(doctor.specialty());
        } else if (request.doctorName() != null && !request.doctorName().isBlank()) {
            existing.setDoctorName(request.doctorName());
            if (request.department() != null) {
                existing.setDepartment(request.department());
            }
        }

        if (request.doctorScheduleId() != null) {
            existing.setDoctorScheduleId(request.doctorScheduleId());
        }

        existing.setAppointmentDateTime(request.appointmentDateTime());
        if (request.status() != null && !request.status().isBlank()) {
            existing.setStatus(request.status());
        }
        existing.setReason(request.reason());

        Appointment updated = appointmentRepository.save(existing);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Appointment existing = getEntityById(id);
        appointmentRepository.delete(existing);
    }
}
