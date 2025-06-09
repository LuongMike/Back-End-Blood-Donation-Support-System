package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.DonationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCenterRepository extends JpaRepository<DonationCenter, Integer> {
}
