package com.example.backend_blood_donation_system.repository;


import com.example.backend_blood_donation_system.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
        Optional<HealthRecord> findByDonation_DonationId(Long donationId);
    }

