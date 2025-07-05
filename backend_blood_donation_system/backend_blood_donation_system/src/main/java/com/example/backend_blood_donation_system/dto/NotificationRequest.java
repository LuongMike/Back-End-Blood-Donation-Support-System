package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private String title;
    private String message;
}