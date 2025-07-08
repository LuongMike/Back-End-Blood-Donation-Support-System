package com.example.backend_blood_donation_system.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Integer centerId;
    private Integer userId;
    private LocalDate scheduledDate;
    private String status;
    private String screeningResult;
    private String remarks;
}