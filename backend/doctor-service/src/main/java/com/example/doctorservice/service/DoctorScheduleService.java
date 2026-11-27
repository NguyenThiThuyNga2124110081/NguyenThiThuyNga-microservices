package com.example.doctorservice.service;

import com.example.doctorservice.dto.DoctorScheduleRequest;
import com.example.doctorservice.dto.DoctorScheduleResponse;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.entity.DoctorSchedule;
import com.example.doctorservice.exception.ResourceNotFoundException;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorScheduleService(DoctorScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public DoctorScheduleResponse toResponse(DoctorSchedule s) {
        return new DoctorScheduleResponse(
                s.getId(),
                s.getDoctorId(),
                s.getWorkDate(),
                s.getShift(),
                s.getStartTime(),
                s.getEndTime(),
                s.getMaxPatients(),
                s.getBookedPatients(),
                s.getStatus(),
                s.getRoomNumber(),
                s.getCreatedAt(),
                s.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<DoctorScheduleResponse> getAll() {
        return scheduleRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DoctorScheduleResponse getById(Long id) {
        DoctorSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch khám với ID: " + id));
        return toResponse(schedule);
    }

    @Transactional(readOnly = true)
    public DoctorSchedule getEntityById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch khám với ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<DoctorScheduleResponse> getByDoctorId(Long doctorId) {
        return scheduleRepository.findByDoctorId(doctorId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorScheduleResponse> getByWorkDate(LocalDate workDate) {
        return scheduleRepository.findByWorkDate(workDate).stream().map(this::toResponse).toList();
    }

    public DoctorScheduleResponse create(DoctorScheduleRequest req) {
        Doctor doctor = doctorRepository.findById(req.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bác sĩ với ID: " + req.doctorId()));
        if (!Boolean.TRUE.equals(doctor.getIsActive())) {
            throw new IllegalArgumentException("Bác sĩ hiện không hoạt động, không thể tạo lịch khám");
        }

        DoctorSchedule schedule = new DoctorSchedule(
                null,
                req.doctorId(),
                req.workDate(),
                req.shift(),
                req.startTime(),
                req.endTime(),
                req.maxPatients(),
                0,
                "AVAILABLE",
                req.roomNumber()
        );
        DoctorSchedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public DoctorScheduleResponse bookSlot(Long scheduleId) {
        DoctorSchedule s = getEntityById(scheduleId);
        if (!"AVAILABLE".equalsIgnoreCase(s.getStatus())) {
            throw new IllegalArgumentException("Lịch khám này không còn khả dụng hoặc đã hết chỗ (Trạng thái: " + s.getStatus() + ")");
        }
        if (s.getBookedPatients() >= s.getMaxPatients()) {
            s.setStatus("FULL");
            scheduleRepository.save(s);
            throw new IllegalArgumentException("Lịch khám đã đạt số lượng tối đa (" + s.getMaxPatients() + " bệnh nhân)");
        }

        s.setBookedPatients(s.getBookedPatients() + 1);
        if (s.getBookedPatients() >= s.getMaxPatients()) {
            s.setStatus("FULL");
        }
        return toResponse(scheduleRepository.save(s));
    }

    public void delete(Long id) {
        DoctorSchedule s = getEntityById(id);
        scheduleRepository.delete(s);
    }
}
