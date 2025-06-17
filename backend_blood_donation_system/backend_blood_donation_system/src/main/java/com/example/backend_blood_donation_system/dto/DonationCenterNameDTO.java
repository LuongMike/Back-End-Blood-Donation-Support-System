package com.example.backend_blood_donation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DonationCenterNameDTO {
    private String name;

    public DonationCenterNameDTO(String name) {
        this.name = name;
    }


}