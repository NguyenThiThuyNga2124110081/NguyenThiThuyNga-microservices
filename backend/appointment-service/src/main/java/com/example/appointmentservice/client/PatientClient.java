package com.example.appointmentservice.client;

import com.example.appointmentservice.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PatientClient {

    private final RestClient restClient;

    public PatientClient(
            RestClient.Builder builder,
            @Value("${services.patient.url:http://localhost:8081}") String url
    ) {
        this.restClient = builder
                .baseUrl(url)
                .build();
    }

    public PatientDTO getById(Long id) {
        return restClient.get()
                .uri("/api/patients/{id}", id)
                .retrieve()
                .body(PatientDTO.class);
    }

    public PatientDTO getPatientById(Long id) {
        return getById(id);
    }
}
