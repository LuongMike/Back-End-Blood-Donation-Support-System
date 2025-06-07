package com.example.backend_blood_donation_system.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BloodRequestDTO {
    private Integer requestId;
    
    @NotBlank(message = "Blood type is required")
    private String bloodType;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @NotBlank(message = "Hospital name is required")
    private String hospitalName;
    
    @NotBlank(message = "Hospital address is required")
    private String hospitalAddress;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime requiredDate;
    private String notes;
    
    // User information
    private Integer requesterId;
    private String requesterName;
    private String requesterEmail;
    private String requesterPhone;
} 