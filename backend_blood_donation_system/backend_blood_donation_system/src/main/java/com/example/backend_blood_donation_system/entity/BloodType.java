package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blood_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate dùng IDENTITY
    private Long blood_type_id;

    private String type;


}
