package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private Double weight;
    private String bloodType;  // ví dụ: "A+", "O-", ...
    private String healthStatus;
}
