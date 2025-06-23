package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class HealthQuestionUpdateDTO {
    private Integer id;
    private String questionText;
    private Boolean correctAnswer;
    private Boolean isActive;
}
