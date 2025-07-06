package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class DonationRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Integer registrationId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "center_id", referencedColumnName = "center_id")
    private DonationCenter center;

    @Column(name = "ready_date")
    private LocalDate readyDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "component_type_id", referencedColumnName = "component_type_id")
    private ComponentType componentType;
}
