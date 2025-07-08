package com.example.backend_blood_donation_system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffDTO {
    private Integer userId;
    private String fullName;
}