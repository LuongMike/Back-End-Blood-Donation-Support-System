package com.example.backend_blood_donation_system.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserProfileRequestDTO {
    private Integer userId;
    private BigDecimal weight;
    private Integer bloodTypeId;
    private LocalDate lastScreeningDate;
    private String healthStatus;
}
