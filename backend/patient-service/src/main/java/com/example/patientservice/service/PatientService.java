package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequest;
import com.example.patientservice.dto.PatientResponse;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getIdentityCardNumber(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAll() {
        return patientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bệnh nhân với ID: " + id));
        return toResponse(patient);
    }

    @Transactional(readOnly = true)
    public Patient getEntityById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bệnh nhân với ID: " + id));
    }

    public PatientResponse create(PatientRequest request) {
        if (request.identityCardNumber() != null && patientRepository.existsByIdentityCardNumber(request.identityCardNumber())) {
            throw new IllegalArgumentException("CCCD/CMND '" + request.identityCardNumber() + "' đã tồn tại trong hệ thống");
        }
        Patient patient = new Patient(
                null,
                request.fullName(),
                request.dateOfBirth(),
                request.gender(),
                request.identityCardNumber(),
                request.phoneNumber(),
                request.email()
        );
        Patient saved = patientRepository.save(patient);
        return toResponse(saved);
    }

    public PatientResponse update(Long id, PatientRequest request) {
        Patient existing = getEntityById(id);

        if (request.identityCardNumber() != null
                && !request.identityCardNumber().equals(existing.getIdentityCardNumber())
                && patientRepository.existsByIdentityCardNumber(request.identityCardNumber())) {
            throw new IllegalArgumentException("CCCD/CMND '" + request.identityCardNumber() + "' đã tồn tại trong hệ thống");
        }

        existing.setFullName(request.fullName());
        existing.setDateOfBirth(request.dateOfBirth());
        existing.setGender(request.gender());
        existing.setIdentityCardNumber(request.identityCardNumber());
        existing.setPhoneNumber(request.phoneNumber());
        existing.setEmail(request.email());

        Patient updated = patientRepository.save(existing);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Patient existing = getEntityById(id);
        patientRepository.delete(existing);
    }
}
