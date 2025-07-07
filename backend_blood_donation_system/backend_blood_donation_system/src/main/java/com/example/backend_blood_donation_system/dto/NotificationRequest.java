package com.example.backend_blood_donation_system.dto;

import com.example.backend_blood_donation_system.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationRequest {
    private String title;
    private String message;
    private NotificationType type;
}