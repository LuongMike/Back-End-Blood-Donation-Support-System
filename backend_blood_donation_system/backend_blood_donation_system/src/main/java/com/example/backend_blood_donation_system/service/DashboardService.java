package com.example.backend_blood_donation_system.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.DashboardStatisticsDTO;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;
import com.example.backend_blood_donation_system.repository.BloodRequestRepository;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;

@Service
public class DashboardService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public DashboardStatisticsDTO getDashboardStatistics() {
        

        // tổng đơn vị máu đang giống với tổng đơn vok trong kho 
        // tổng số yêu cầu 
        //-> yêu cầu bình thườn 
        //-> yêu cầu khẩn cấp 
        //-> người yêu cầu hôm nay 
        // 1. Lấy tổng số đơn vị máu đã hiến
        long totalDonatedUnits = donationHistoryRepository.sumTotalDonatedUnits().orElse(0L);

        // 2. Lấy số yêu cầu khẩn cấp đang hoạt động
        long urgentRequestsCount = bloodRequestRepository.countUrgentAndActiveRequests();

        // 3. Lấy số yêu cầu bình thường đang hoạt động
        long normalRequestsCount = bloodRequestRepository.countNormalAndActiveRequests();

        // 4. Lấy số người hiến máu trong hôm nay
        long donorsTodayCount = donationHistoryRepository.countByDonationDate(LocalDate.now());

        
        
        return new DashboardStatisticsDTO(
            totalDonatedUnits, 
            urgentRequestsCount, 
            normalRequestsCount,  // Thêm số lượng yêu cầu bình thường
            donorsTodayCount
        );
    }
}