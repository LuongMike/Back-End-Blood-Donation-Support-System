package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HealthQuestion", schema = "dbo") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "correct_answer")
    private Boolean correctAnswer;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}

