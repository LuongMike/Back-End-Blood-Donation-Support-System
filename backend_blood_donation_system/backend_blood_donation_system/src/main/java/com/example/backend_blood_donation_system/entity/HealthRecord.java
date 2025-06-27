package com.example.backend_blood_donation_system.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private DonationHistory donation;

    private String bloodPressure;
    private Integer heartRate;
    private Float hemoglobinLevel;
    private BigDecimal weight;
    private Float temperature;
    private String hydrationStatus;
    private String postDonationReaction;
    private String additionalNotes;
    private String recordedBy;

    private LocalDateTime recordedAt = LocalDateTime.now();
}

