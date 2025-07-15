package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class ForumPostDTO {
    private Long id;
    private Long topicId;
    private String content;
    private Long authorId;
    private Boolean visible;
}

