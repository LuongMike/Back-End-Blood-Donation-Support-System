package com.example.backend_blood_donation_system.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer donationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private DonationCenter center;

    @ManyToOne
    @JoinColumn(name = "blood_type_id")
    private BloodType bloodType;

    @ManyToOne
    @JoinColumn(name = "component_type_id")
    private ComponentType componentType;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private LocalDate donationDate;
    private Integer units;
}
