package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.HealthAnswerRequest;
import com.example.backend_blood_donation_system.entity.HealthAnswer;
import com.example.backend_blood_donation_system.entity.HealthQuestion;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.HealthAnswerRepository;
import com.example.backend_blood_donation_system.repository.HealthQuestionRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

@Service
public class HealthAnswerService {

    @Autowired
    private HealthAnswerRepository healthAnswerRepository;

    @Autowired
    private HealthQuestionRepository healthQuestionRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveAnswers(Integer userId, List<HealthAnswerRequest> answers) {
        for (HealthAnswerRequest answerRequest : answers) {
            HealthQuestion question = healthQuestionRepository.findById(answerRequest.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy câu hỏi với ID: " + answerRequest.getQuestionId()));

            HealthAnswer answer = new HealthAnswer();
            User user = new User();
            user.setUserId(userId); // chỉ cần set ID để ánh xạ

            answer.setUser(user);
            answer.setHealthQuestion(question);
            answer.setAnswerValue(answerRequest.isAnswerValue());
            answer.setCreatedAt(LocalDateTime.now());

            healthAnswerRepository.save(answer);
        }

        boolean allCorrect = answers.stream()
                .allMatch(a -> {
                    HealthQuestion question = healthQuestionRepository.findById(a.getQuestionId())
                            .orElse(null);
                    return question != null && a.isAnswerValue() == question.getCorrectAnswer();
                });

        if (!allCorrect) {
            throw new IllegalArgumentException("Không đủ điều kiện để hiến máu. Bạn cần trả lời 'Có' cho tất cả câu hỏi.");
        }
    }

    public List<HealthQuestion> getActiveQuestion(){
        return healthQuestionRepository.findByIsActiveTrue();
    }
}