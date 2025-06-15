package com.example.backend_blood_donation_system.dto;
import java.time.LocalDate;
import lombok.Data;
@Data

public class HealthCheckRequest {
    private Long userId;
    private Double weight;
    private Integer bloodTypeId;
    private LocalDate lastScreeningDate;
    private String healthStatus;
}
