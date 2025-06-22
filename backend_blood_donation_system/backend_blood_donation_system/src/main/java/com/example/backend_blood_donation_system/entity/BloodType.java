package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BloodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bloodTypeId;

    @Column(unique = true)
    private String type;
}
