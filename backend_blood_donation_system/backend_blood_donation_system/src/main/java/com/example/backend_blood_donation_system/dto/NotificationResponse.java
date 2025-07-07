package com.example.backend_blood_donation_system.dto;

import com.example.backend_blood_donation_system.enums.NotificationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Integer notificationId;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
    private NotificationType type;
}
