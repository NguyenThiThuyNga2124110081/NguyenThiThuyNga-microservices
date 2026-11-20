package com.example.patientservice.service;

import com.example.patientservice.entity.Patient;
import com.example.patientservice.entity.PatientAddress;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.repository.PatientAddressRepository;
import com.example.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientAddressService {

    private final PatientAddressRepository patientAddressRepository;
    private final PatientRepository patientRepository;

    public PatientAddressService(PatientAddressRepository patientAddressRepository, PatientRepository patientRepository) {
        this.patientAddressRepository = patientAddressRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientAddress> getAll() {
        return patientAddressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PatientAddress getById(Long id) {
        return patientAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient address not found with id: " + id));
    }

    public PatientAddress create(PatientAddress address) {
        Long targetPatientId = address.getPatientId();
        if (targetPatientId == null && address.getPatient() != null) {
            targetPatientId = address.getPatient().getId();
        }
        if (targetPatientId == null) {
            throw new IllegalArgumentException("Patient ID is required for patient address");
        }
        final Long patientIdToFind = targetPatientId;
        Patient patient = patientRepository.findById(patientIdToFind)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientIdToFind));
        address.setPatient(patient);
        return patientAddressRepository.save(address);
    }

    public PatientAddress update(Long id, PatientAddress details) {
        PatientAddress existing = getById(id);

        Long targetPatientId = details.getPatientId();
        if (targetPatientId == null && details.getPatient() != null) {
            targetPatientId = details.getPatient().getId();
        }
        if (targetPatientId != null && !targetPatientId.equals(existing.getPatient().getId())) {
            final Long patientIdToFind = targetPatientId;
            Patient patient = patientRepository.findById(patientIdToFind)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientIdToFind));
            existing.setPatient(patient);
        }

        existing.setAddressLine(details.getAddressLine());
        existing.setWard(details.getWard());
        existing.setDistrict(details.getDistrict());
        existing.setCity(details.getCity());
        if (details.getIsDefault() != null) {
            existing.setIsDefault(details.getIsDefault());
        }

        return patientAddressRepository.save(existing);
    }

    public void delete(Long id) {
        PatientAddress existing = getById(id);
        patientAddressRepository.delete(existing);
    }
}
