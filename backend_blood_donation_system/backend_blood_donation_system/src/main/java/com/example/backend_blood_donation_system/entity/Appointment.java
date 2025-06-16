package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="Appointment")
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Giả sử đã có entity User

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private DonationCenter center;  // Giả sử đã có entity DonationCenter

    private java.sql.Date scheduledDate;

    @Column(name = "status",length = 20)
    private String status = "PENDING"; // default khi tạo mới

    @Column(length = 50)
    private String screeningResult;

    @Lob
    private String remarks;
}
