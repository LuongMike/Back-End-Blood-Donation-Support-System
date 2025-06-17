package com.example.backend_blood_donation_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.HealthQuestion;
import com.example.backend_blood_donation_system.repository.HealthQuestionRepository;

@Service
public class HealthQuestionService {

    @Autowired
    private HealthQuestionRepository healthQuestionRepository;

    public List<HealthQuestion> getActiveQuestions() {
        return healthQuestionRepository.findByIsActiveTrue();
    }

    public HealthQuestion createQuestion(HealthQuestion question) {
        return healthQuestionRepository.save(question);
    }
}
