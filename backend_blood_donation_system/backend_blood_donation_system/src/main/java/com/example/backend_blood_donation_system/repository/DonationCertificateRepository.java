package com.example.backend_blood_donation_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.DonationCertificate;

public interface DonationCertificateRepository extends JpaRepository<DonationCertificate, Long> {
    List<DonationCertificate> findByUser_UserIdOrderByIssueDateDesc(Integer userId);
    Optional<DonationCertificate> findByCertificateCode(String code);
}
