package com.example.backend_blood_donation_system.dto;

import lombok.Getter;

@Getter
public class AppointmentRequestDTO {

    private Integer userId;
    private Integer centerId;
    private String scheduledDate;
}
