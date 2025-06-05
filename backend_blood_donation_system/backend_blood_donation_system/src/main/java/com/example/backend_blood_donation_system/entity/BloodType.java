package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BloodType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodType {
    @Id
    private Long blood_type_id;

    private String type;


}
