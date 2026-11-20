package com.example.patientservice.repository;

import com.example.patientservice.entity.PatientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAddressRepository extends JpaRepository<PatientAddress, Long> {
    List<PatientAddress> findByPatientId(Long patientId);
}
