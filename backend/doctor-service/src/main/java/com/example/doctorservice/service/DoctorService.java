package com.example.doctorservice.service;

import com.example.doctorservice.dto.DoctorRequest;
import com.example.doctorservice.dto.DoctorResponse;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.exception.ResourceNotFoundException;
import com.example.doctorservice.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFullName(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getConsultationFee(),
                doctor.getIsActive(),
                doctor.getCreatedAt(),
                doctor.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getAll() {
        return doctorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponse getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bác sĩ với ID: " + id));
        return toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public Doctor getEntityById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bác sĩ với ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty).stream().map(this::toResponse).toList();
    }

    public DoctorResponse create(DoctorRequest request) {
        Doctor doctor = new Doctor(
                null,
                request.fullName(),
                request.specialty(),
                request.phoneNumber(),
                request.email(),
                request.consultationFee(),
                request.isActive()
        );
        Doctor saved = doctorRepository.save(doctor);
        return toResponse(saved);
    }

    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor existing = getEntityById(id);
        existing.setFullName(request.fullName());
        existing.setSpecialty(request.specialty());
        existing.setPhoneNumber(request.phoneNumber());
        existing.setEmail(request.email());
        if (request.consultationFee() != null) {
            existing.setConsultationFee(request.consultationFee());
        }
        if (request.isActive() != null) {
            existing.setIsActive(request.isActive());
        }
        Doctor updated = doctorRepository.save(existing);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Doctor existing = getEntityById(id);
        doctorRepository.delete(existing);
    }
}
