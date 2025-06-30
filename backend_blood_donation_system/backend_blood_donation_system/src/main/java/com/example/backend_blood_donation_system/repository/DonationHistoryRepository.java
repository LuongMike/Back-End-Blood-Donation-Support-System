package com.example.backend_blood_donation_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.DonationHistory;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Integer> {
    List<DonationHistory> findByUser_UserId(Long userId);
}
