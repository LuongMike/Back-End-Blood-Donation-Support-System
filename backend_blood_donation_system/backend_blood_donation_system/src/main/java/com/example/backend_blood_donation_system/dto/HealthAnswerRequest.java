package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class HealthAnswerRequest {
    private Integer questionId;
    private boolean answerValue;
}
