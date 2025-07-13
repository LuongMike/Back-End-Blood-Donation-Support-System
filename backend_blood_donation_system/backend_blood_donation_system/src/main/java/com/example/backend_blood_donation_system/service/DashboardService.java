package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.DashboardStatisticsDTO;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;
import com.example.backend_blood_donation_system.repository.BloodRequestRepository;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public DashboardStatisticsDTO getDashboardStatistics() {
        
        // 1. Lấy tổng số đơn vị máu đã hiến
        long totalDonatedUnits = donationHistoryRepository.sumTotalDonatedUnits().orElse(0L);

        // 2. Lấy số yêu cầu khẩn cấp đang hoạt động
        long urgentRequestsCount = bloodRequestRepository.countUrgentAndActiveRequests();

        // 3. Lấy số người hiến máu trong hôm nay
        long donorsTodayCount = donationHistoryRepository.countByDonationDate(LocalDate.now());

        // 4. Lấy tổng số đơn vị máu có sẵn trong kho
        long availableBloodUnits = bloodInventoryRepository.sumTotalUnitsAvailable().orElse(0L);
        
        return new DashboardStatisticsDTO(
            totalDonatedUnits, 
            urgentRequestsCount, 
            donorsTodayCount, 
            availableBloodUnits
        );
    }
}