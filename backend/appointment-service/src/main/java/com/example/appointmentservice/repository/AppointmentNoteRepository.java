package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.AppointmentNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentNoteRepository extends JpaRepository<AppointmentNote, Long> {
    List<AppointmentNote> findByAppointmentId(Long appointmentId);
}
