package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearbyDonorDTO {
    private Integer userId;
    private String fullName;
    private String bloodType;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distanceInKm;
}
