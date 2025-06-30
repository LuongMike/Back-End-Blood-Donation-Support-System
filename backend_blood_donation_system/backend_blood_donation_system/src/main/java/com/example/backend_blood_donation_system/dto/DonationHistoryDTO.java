package com.example.backend_blood_donation_system.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DonationHistoryDTO {
    private Integer donationId;
    private String userFullName;
    private String centerName;
    private String bloodType;
    private String componentType;
    private LocalDate donationDate;
    private Integer units;
}
