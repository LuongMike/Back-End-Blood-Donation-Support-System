package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.HealthAnswerSubmission;
import com.example.backend_blood_donation_system.dto.HealthQuestionUpdateDTO;
import com.example.backend_blood_donation_system.entity.HealthQuestion;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.HealthAnswerService;
import com.example.backend_blood_donation_system.service.HealthQuestionService;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @Autowired
    private HealthAnswerService healthAnswerService;

    @Autowired
    private HealthQuestionService healthQuestionService;

    public HealthController(HealthQuestionService healthQuestionService) {
        this.healthQuestionService = healthQuestionService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<HealthQuestion>> getQuestions() {
        return ResponseEntity.ok(healthAnswerService.getActiveQuestion());
    }

    @PostMapping("/questions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HealthQuestion> createQuestion(@RequestBody HealthQuestion question) {
        return ResponseEntity.ok(healthQuestionService.createQuestion(question));
    }

    @PostMapping("/answers")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<?> submitAnswers(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody HealthAnswerSubmission submission) {
        Integer userId = userDetails.getId();
        healthAnswerService.saveAnswers(userId, submission.getAnswers());
        return ResponseEntity.ok("Câu trả lời đã được gửi thành công. Tiếp tục đến bước đặt lịch hẹn.");
    }

    @PutMapping("/questions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateQuestion(@RequestBody HealthQuestionUpdateDTO dto) {
        HealthQuestion updatedQuestion = healthQuestionService.updateQuestion(dto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/questions/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id) {
        healthQuestionService.deleteQuestion(id);
        return ResponseEntity.ok("Câu hỏi đã được xóa thành công.");
    }
}
