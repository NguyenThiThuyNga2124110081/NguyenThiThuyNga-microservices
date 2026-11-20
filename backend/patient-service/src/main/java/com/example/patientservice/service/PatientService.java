package com.example.patientservice.service;

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

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public Patient create(Patient patient) {
        if (patient.getIdentityCardNumber() != null && patientRepository.existsByIdentityCardNumber(patient.getIdentityCardNumber())) {
            throw new IllegalArgumentException("CCCD/CMND '" + patient.getIdentityCardNumber() + "' đã tồn tại trong hệ thống");
        }
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient patientDetails) {
        Patient existing = getById(id);

        if (patientDetails.getIdentityCardNumber() != null
                && !patientDetails.getIdentityCardNumber().equals(existing.getIdentityCardNumber())
                && patientRepository.existsByIdentityCardNumber(patientDetails.getIdentityCardNumber())) {
            throw new IllegalArgumentException("CCCD/CMND '" + patientDetails.getIdentityCardNumber() + "' đã tồn tại trong hệ thống");
        }

        existing.setFullName(patientDetails.getFullName());
        existing.setDateOfBirth(patientDetails.getDateOfBirth());
        existing.setGender(patientDetails.getGender());
        existing.setIdentityCardNumber(patientDetails.getIdentityCardNumber());
        existing.setPhoneNumber(patientDetails.getPhoneNumber());
        existing.setEmail(patientDetails.getEmail());

        return patientRepository.save(existing);
    }

    public void delete(Long id) {
        Patient existing = getById(id);
        patientRepository.delete(existing);
    }
}
