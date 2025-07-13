package com.example.backend_blood_donation_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.entity.User;


public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Integer> {
    List<DonationHistory> findByUser_UserId(Long userId);


    Optional<DonationHistory> findTopByUserOrderByDonationDateDesc(User user);

     // MỚI: Đếm số lượt hiến máu trong một ngày cụ thể
    long countByDonationDate(LocalDate date);

    // MỚI: Tính tổng số đơn vị máu đã được hiến từ trước đến nay
    @Query("SELECT SUM(dh.units) FROM DonationHistory dh")
    Optional<Long> sumTotalDonatedUnits();
}
