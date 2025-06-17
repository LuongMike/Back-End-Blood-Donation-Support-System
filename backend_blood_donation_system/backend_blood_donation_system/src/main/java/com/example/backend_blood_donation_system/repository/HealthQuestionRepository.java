package com.example.backend_blood_donation_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.HealthQuestion;

public interface HealthQuestionRepository extends JpaRepository<HealthQuestion, Integer> {

    // Custom query to find all active questions
    List<HealthQuestion> findByIsActiveTrue();
    Optional<HealthQuestion> findByIdAndIsActiveTrue(Integer id);
}
