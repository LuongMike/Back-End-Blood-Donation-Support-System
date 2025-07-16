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
public class ForumTopicDTO {
    private Long id;
    private String title;
    private String description;
    private Long createdBy;
    private LocalDateTime createdAt;
    private int postCount; 
}

