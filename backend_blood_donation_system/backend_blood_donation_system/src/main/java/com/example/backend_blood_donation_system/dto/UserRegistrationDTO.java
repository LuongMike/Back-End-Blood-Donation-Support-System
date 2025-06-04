package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String username;  // thêm trường username
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
}