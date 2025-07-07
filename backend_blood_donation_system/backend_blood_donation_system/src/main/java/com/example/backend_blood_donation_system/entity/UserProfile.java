
package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore // ✅ Đặt ở đây để tránh vòng lặp JSON
    private User user;

    @ManyToOne
    @JoinColumn(name = "blood_type_id", referencedColumnName = "blood_type_id")
    private BloodType bloodType;

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "last_screening_date")
    private LocalDate lastScreeningDate;

    @Column(name = "health_status")
    private String healthStatus;
}
