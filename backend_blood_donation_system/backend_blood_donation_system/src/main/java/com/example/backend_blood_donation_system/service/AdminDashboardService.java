package com.example.backend_blood_donation_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.AdminDashboardDTO;
import com.example.backend_blood_donation_system.dto.ChartDataDTO;
import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.repository.AppointmentRepository;
import com.example.backend_blood_donation_system.repository.BloodRequestRepository;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {
    private final UserRepository userRepository;
    private final DonationHistoryRepository donationHistoryRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminDashboardDTO getDashboardData() {
        Long totalUsers = userRepository.count();
        Long totalDonations = donationHistoryRepository.count();
        Long pendingBloodRequests = bloodRequestRepository.countByStatus(BloodRequest.RequestStatus.PENDING);
        Long completedBloodRequests = bloodRequestRepository.countByStatus(BloodRequest.RequestStatus.COMPLETED);
        Long appointmentsToday = appointmentRepository.countByScheduledDate(java.sql.Date.valueOf(LocalDate.now()));

        return AdminDashboardDTO.builder()
                .totalUsers(totalUsers)
                .totalBloodDonations(totalDonations)
                .pendingBloodRequests(pendingBloodRequests)
                .completedBloodRequests(completedBloodRequests)
                .appointmentsToday(appointmentsToday)
                .build();
    }

    public List<ChartDataDTO> getMonthlyDonationsChartData() {
        // 1. Lấy dữ liệu từ DB trong 6 tháng gần nhất
        LocalDate  sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<ChartDataDTO> dbData = donationHistoryRepository.countDonationsByMonth(sixMonthsAgo);
        Map<String, Long> dataMap = dbData.stream()
                .collect(Collectors.toMap(ChartDataDTO::getLabel, ChartDataDTO::getValue));

        // 2. Tạo danh sách đầy đủ 6 tháng (kể cả tháng không có dữ liệu)
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter labelFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return IntStream.range(0, 6)
                .mapToObj(i -> LocalDate.now().minusMonths(i))
                .map(date -> {
                    String dbKey = date.format(dbFormatter);
                    String label = date.format(labelFormatter);
                    Long value = dataMap.getOrDefault(dbKey, 0L); // Nếu tháng không có, trả về 0
                    return new ChartDataDTO(label, value);
                })
                .sorted((a, b) -> LocalDate.parse("01/" + a.getLabel(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        .compareTo(LocalDate.parse("01/" + b.getLabel(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .collect(Collectors.toList());
    }

     public List<ChartDataDTO> getMonthlyRequestsChartData() {
        // SỬA LỖI Ở ĐÂY: Chuyển từ LocalDate sang LocalDateTime
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);

        List<ChartDataDTO> dbData = bloodRequestRepository.countRequestsByMonth(sixMonthsAgo);

        // ... phần còn lại của phương thức giữ nguyên
        Map<String, Long> dataMap = dbData.stream()
            .collect(Collectors.toMap(ChartDataDTO::getLabel, ChartDataDTO::getValue));

        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter labelFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return IntStream.range(0, 6)
            .mapToObj(i -> LocalDate.now().minusMonths(i))
            .map(date -> {
                String dbKey = date.format(dbFormatter);
                String label = date.format(labelFormatter);
                Long value = dataMap.getOrDefault(dbKey, 0L);
                return new ChartDataDTO(label, value);
            })
            .sorted((a, b) -> LocalDate.parse("01/" + a.getLabel(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                     .compareTo(LocalDate.parse("01/" + b.getLabel(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
            .collect(Collectors.toList());
    }
}
