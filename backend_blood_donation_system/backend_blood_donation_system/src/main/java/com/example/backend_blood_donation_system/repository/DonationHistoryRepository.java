package com.example.backend_blood_donation_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_blood_donation_system.dto.ChartDataDTO;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.entity.User;


public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Integer> {
    List<DonationHistory> findByUser_UserId(Long userId);

    Optional<DonationHistory> findTopByUserOrderByDonationDateDesc(User user);


     // MỚI: Đếm số lượt hiến máu trong một ngày cụ thể
    long countByDonationDate(LocalDate date);

    // MỚI: Tính tổng số đơn vị máu đã được hiến từ trước đến nay
    //STAFF DASHBOARD
    @Query("SELECT SUM(dh.units) FROM DonationHistory dh")
    Optional<Long> sumTotalDonatedUnits();

    @Query("SELECT new com.example.backend_blood_donation_system.dto.ChartDataDTO(" +
           "FORMAT(dh.donationDate, 'yyyy-MM'), " +
           "COUNT(dh.id)) " +
           "FROM DonationHistory dh " +
           "WHERE dh.donationDate >= :startDate " + // Bây giờ câu lệnh sẽ khớp
           "GROUP BY FORMAT(dh.donationDate, 'yyyy-MM') " +
           "ORDER BY FORMAT(dh.donationDate, 'yyyy-MM') ASC")
    List<ChartDataDTO> countDonationsByMonth(@Param("startDate") LocalDate startDate);

}
