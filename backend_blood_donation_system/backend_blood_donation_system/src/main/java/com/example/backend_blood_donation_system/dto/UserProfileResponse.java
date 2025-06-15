package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;
} 