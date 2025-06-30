package com.example.backend_blood_donation_system.dto;

import lombok.Data;

@Data
public class BloodRequestDTO {
    private Integer bloodTypeId;
    private Integer componentTypeId;
    private Integer quantity;
    private String type; // NORMAL or URGENT
}