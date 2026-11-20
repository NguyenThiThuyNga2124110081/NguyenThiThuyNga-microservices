package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.AppointmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetail, Long> {
    List<AppointmentDetail> findByAppointmentId(Long appointmentId);
}
