package com.example.backend_blood_donation_system.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    
    // THAY ĐỔI Ở ĐÂY: Dùng đối tượng thay vì ID
    private CenterSummaryDTO center;
    private UserSummaryDTO user;

    private LocalDate scheduledDate;
    private String status;
    private String screeningResult;
    private String remarks;
}