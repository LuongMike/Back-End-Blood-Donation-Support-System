package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodInventorySummaryDTO {
    private Integer bloodTypeId;
    private Integer unitsAvailable;
}
