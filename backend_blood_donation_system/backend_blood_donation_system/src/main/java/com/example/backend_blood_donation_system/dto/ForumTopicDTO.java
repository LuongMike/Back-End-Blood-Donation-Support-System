package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class ForumTopicDTO {
    private Long id;
    private String title;
    private String description;
    private Long createdBy;
}

