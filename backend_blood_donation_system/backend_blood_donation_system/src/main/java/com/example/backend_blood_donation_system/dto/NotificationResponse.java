package com.example.backend_blood_donation_system.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
}
