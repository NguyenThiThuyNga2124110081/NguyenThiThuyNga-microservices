package com.example.appointmentservice.client;

import com.example.appointmentservice.dto.DoctorResponse;
import com.example.appointmentservice.dto.DoctorScheduleDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DoctorClient {

    private final RestClient restClient;

    public DoctorClient(
            RestClient.Builder builder,
            @Value("${services.doctor.url:http://localhost:8083}") String url
    ) {
        this.restClient = builder
                .baseUrl(url)
                .build();
    }

    public DoctorResponse getById(Long id) {
        return restClient.get()
                .uri("/api/doctors/{id}", id)
                .retrieve()
                .body(DoctorResponse.class);
    }

    public DoctorScheduleDTO getScheduleById(Long scheduleId) {
        return restClient.get()
                .uri("/api/doctor-schedules/{id}", scheduleId)
                .retrieve()
                .body(DoctorScheduleDTO.class);
    }

    public DoctorScheduleDTO bookSlot(Long scheduleId) {
        return restClient.post()
                .uri("/api/doctor-schedules/{id}/book", scheduleId)
                .retrieve()
                .body(DoctorScheduleDTO.class);
    }
}
