package com.example.backend_blood_donation_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodInventoryDTO {

    @NotNull
    private Integer centerId;

    @NotNull
    private Integer bloodTypeId;

    @NotNull
    private Integer componentTypeId;

    @NotNull
    @Min(0)
    private Integer unitsAvailable;
}


