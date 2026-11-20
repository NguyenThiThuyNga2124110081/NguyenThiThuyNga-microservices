package com.example.patientservice.service;

import com.example.patientservice.entity.Patient;
import com.example.patientservice.entity.PatientMedicalHistory;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.repository.PatientMedicalHistoryRepository;
import com.example.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientMedicalHistoryService {

    private final PatientMedicalHistoryRepository patientMedicalHistoryRepository;
    private final PatientRepository patientRepository;

    public PatientMedicalHistoryService(PatientMedicalHistoryRepository patientMedicalHistoryRepository, PatientRepository patientRepository) {
        this.patientMedicalHistoryRepository = patientMedicalHistoryRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientMedicalHistory> getAll() {
        return patientMedicalHistoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PatientMedicalHistory getById(Long id) {
        return patientMedicalHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient medical history not found with id: " + id));
    }

    public PatientMedicalHistory create(PatientMedicalHistory history) {
        Long targetPatientId = history.getPatientId();
        if (targetPatientId == null && history.getPatient() != null) {
            targetPatientId = history.getPatient().getId();
        }
        if (targetPatientId == null) {
            throw new IllegalArgumentException("Patient ID is required for patient medical history");
        }
        final Long patientIdToFind = targetPatientId;
        Patient patient = patientRepository.findById(patientIdToFind)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientIdToFind));
        history.setPatient(patient);
        return patientMedicalHistoryRepository.save(history);
    }

    public PatientMedicalHistory update(Long id, PatientMedicalHistory details) {
        PatientMedicalHistory existing = getById(id);

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

        existing.setConditionName(details.getConditionName());
        existing.setDiagnosedDate(details.getDiagnosedDate());
        existing.setTreatment(details.getTreatment());
        existing.setNotes(details.getNotes());

        return patientMedicalHistoryRepository.save(existing);
    }

    public void delete(Long id) {
        PatientMedicalHistory existing = getById(id);
        patientMedicalHistoryRepository.delete(existing);
    }
}
