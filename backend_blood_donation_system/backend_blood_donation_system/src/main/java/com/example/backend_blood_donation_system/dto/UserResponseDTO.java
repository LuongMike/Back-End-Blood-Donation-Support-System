package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String address;
    private String role;
    private String status;
}