package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NearbyDonorDTO {
    private Integer userId;
    private String fullName;
    private String email;
    private String address;
    private Double latitude;
    private Double longitude;
    private String bloodType;
}