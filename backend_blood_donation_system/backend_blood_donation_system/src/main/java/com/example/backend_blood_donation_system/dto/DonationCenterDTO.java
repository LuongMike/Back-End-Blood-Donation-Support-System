package com.example.backend_blood_donation_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DonationCenterDTO {
    private int center_id;
    private String name;
    private String address;
    private Float latitude;
    private Float longitude;

    public DonationCenterDTO(int center_id,String name, String address, Float latitude, Float longitude) {
        this.center_id=center_id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}