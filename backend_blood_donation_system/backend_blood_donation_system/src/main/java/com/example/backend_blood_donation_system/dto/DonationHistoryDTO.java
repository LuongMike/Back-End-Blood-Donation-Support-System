package com.example.backend_blood_donation_system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DonationHistoryDTO {
    private Long donationId;
    private String userFullName;
    private String centerName;
    private String bloodType;
    private String componentType;
    private LocalDate donationDate;
    private Integer volumeMl;
}
