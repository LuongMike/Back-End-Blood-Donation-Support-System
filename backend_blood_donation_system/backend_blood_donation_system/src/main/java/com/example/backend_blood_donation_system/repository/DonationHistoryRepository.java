package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {
    List<DonationHistory> findByUser_UserId(Long userId);
}
