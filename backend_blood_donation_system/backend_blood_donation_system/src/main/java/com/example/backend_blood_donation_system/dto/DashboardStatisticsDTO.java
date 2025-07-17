package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatisticsDTO {
    private long totalDonatedUnits;     // Tổng đơn vị máu đã hiến
    private long urgentRequestsCount;   // Số yêu cầu khẩn cấp
    private long donorsTodayCount;      // Số người hiến máu hôm nay
    private long availableBloodUnits;   // Tổng đơn vị máu sẵn có trong kho
}