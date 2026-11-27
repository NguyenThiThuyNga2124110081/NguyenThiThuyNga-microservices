package com.example.doctorservice.repository;

import com.example.doctorservice.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorId(Long doctorId);
    List<DoctorSchedule> findByDoctorIdAndWorkDate(Long doctorId, LocalDate workDate);
    List<DoctorSchedule> findByWorkDate(LocalDate workDate);
}
