package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {
    boolean existsByToken(String token);
}
