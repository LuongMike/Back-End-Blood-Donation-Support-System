package com.example.backend_blood_donation_system.entity;


import com.example.backend_blood_donation_system.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 50)
    private NotificationType type;
}