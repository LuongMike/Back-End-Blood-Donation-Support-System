package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "donation_center")
@Data
public class DonationCenter {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer centerId;

    @Column(length = 100)
    private String name;

    @Lob
    private String address;

    private Float latitude;

    private Float longitude;
}
