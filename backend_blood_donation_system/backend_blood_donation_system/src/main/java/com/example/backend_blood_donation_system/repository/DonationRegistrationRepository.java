package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend_blood_donation_system.entity.DonationRegistration;

public interface DonationRegistrationRepository extends JpaRepository<DonationRegistration, Integer> {




}
