package com.example.backend_blood_donation_system.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumPostDTO {
    private Long id;
    private Long topicId;
    private String content;
    private Long authorId;
    private Boolean visible;
    private LocalDateTime createdAt;
}

