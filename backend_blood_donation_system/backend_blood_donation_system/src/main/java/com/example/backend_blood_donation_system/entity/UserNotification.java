package com.example.backend_blood_donation_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
//    @JoinColumn(name = "user_user_id")
    private User user;

    @ManyToOne
//    @JoinColumn(name = "notification_id")
    private Notification notification;

    private boolean isRead = false;
}
