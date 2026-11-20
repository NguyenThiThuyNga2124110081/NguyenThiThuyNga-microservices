package com.example.patientservice.service;

import com.example.patientservice.entity.Patient;
import com.example.patientservice.entity.PatientContact;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.repository.PatientContactRepository;
import com.example.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientContactService {

    private final PatientContactRepository patientContactRepository;
    private final PatientRepository patientRepository;

    public PatientContactService(PatientContactRepository patientContactRepository, PatientRepository patientRepository) {
        this.patientContactRepository = patientContactRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientContact> getAll() {
        return patientContactRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PatientContact getById(Long id) {
        return patientContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient contact not found with id: " + id));
    }

    public PatientContact create(PatientContact contact) {
        Long targetPatientId = contact.getPatientId();
        if (targetPatientId == null && contact.getPatient() != null) {
            targetPatientId = contact.getPatient().getId();
        }
        if (targetPatientId == null) {
            throw new IllegalArgumentException("Patient ID is required for patient contact");
        }
        final Long patientIdToFind = targetPatientId;
        Patient patient = patientRepository.findById(patientIdToFind)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientIdToFind));
        contact.setPatient(patient);
        return patientContactRepository.save(contact);
    }

    public PatientContact update(Long id, PatientContact details) {
        PatientContact existing = getById(id);

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

        existing.setContactName(details.getContactName());
        existing.setRelationship(details.getRelationship());
        existing.setPhoneNumber(details.getPhoneNumber());
        existing.setEmail(details.getEmail());
        if (details.getIsEmergency() != null) {
            existing.setIsEmergency(details.getIsEmergency());
        }

        return patientContactRepository.save(existing);
    }

    public void delete(Long id) {
        PatientContact existing = getById(id);
        patientContactRepository.delete(existing);
    }
}
