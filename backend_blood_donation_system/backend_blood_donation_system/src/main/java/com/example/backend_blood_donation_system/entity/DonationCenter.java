package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(length = 255)
    private String address;

    private Float latitude;

    private Float longitude;
}
