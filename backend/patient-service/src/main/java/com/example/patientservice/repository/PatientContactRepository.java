package com.example.patientservice.repository;

import com.example.patientservice.entity.PatientContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientContactRepository extends JpaRepository<PatientContact, Long> {
    List<PatientContact> findByPatientId(Long patientId);
}
