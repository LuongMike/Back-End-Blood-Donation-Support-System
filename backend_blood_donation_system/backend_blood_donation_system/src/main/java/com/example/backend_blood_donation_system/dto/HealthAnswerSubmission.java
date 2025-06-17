package com.example.backend_blood_donation_system.dto;

import java.util.List;

import lombok.Data;

@Data
public class HealthAnswerSubmission {
    private List<HealthAnswerRequest> answers;

    public List<HealthAnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<HealthAnswerRequest> answers) {
        this.answers = answers;
    }
}
