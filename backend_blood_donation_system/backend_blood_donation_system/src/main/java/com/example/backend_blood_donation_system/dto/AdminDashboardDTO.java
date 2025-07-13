package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardDTO {
    private Long totalUsers;
    private Long totalBloodDonations;
    private Long pendingBloodRequests;
    private Long completedBloodRequests;
    private Long appointmentsToday;
}