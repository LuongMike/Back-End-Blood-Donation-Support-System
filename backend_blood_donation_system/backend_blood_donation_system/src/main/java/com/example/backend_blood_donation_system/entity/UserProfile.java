package com.example.backend_blood_donation_system.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "UserProfile")

public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(nullable = false)
    private Long userId;
    private Double weight;
    private int bloodTypeId;
    private LocalDate lastScreeningDate;
    private String healthStatus;


}
