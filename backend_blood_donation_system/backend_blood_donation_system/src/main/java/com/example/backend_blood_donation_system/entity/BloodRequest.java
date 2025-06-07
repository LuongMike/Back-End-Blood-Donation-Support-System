package com.example.backend_blood_donation_system.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blood_request", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(name = "hospital_address", nullable = false)
    private String hospitalAddress;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "status")
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "required_date")
    private LocalDateTime requiredDate;

    @Column(name = "notes")
    private String notes;
} 