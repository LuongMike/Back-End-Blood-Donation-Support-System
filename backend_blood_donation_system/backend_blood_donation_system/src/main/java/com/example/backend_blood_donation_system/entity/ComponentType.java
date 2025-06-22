package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ComponentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentTypeId;

    private String name;
    private String description;
}
