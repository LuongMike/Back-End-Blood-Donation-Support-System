package com.example.backend_blood_donation_system.repository;

import java.util.List;
import java.util.Optional;

import com.example.backend_blood_donation_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.entity.User;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Integer> {
    List<DonationHistory> findByUser_UserId(Long userId);


    Optional<DonationHistory> findTopByUserOrderByDonationDateDesc(User user);

}
