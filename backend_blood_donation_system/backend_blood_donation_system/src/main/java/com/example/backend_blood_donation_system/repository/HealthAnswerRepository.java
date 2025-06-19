package com.example.backend_blood_donation_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.HealthAnswer;

public interface HealthAnswerRepository  extends JpaRepository<HealthAnswer, Integer> {

    // Custom query to find answers by user ID
    List<HealthAnswer> findByUser_UserId(Integer userId);
    
}
