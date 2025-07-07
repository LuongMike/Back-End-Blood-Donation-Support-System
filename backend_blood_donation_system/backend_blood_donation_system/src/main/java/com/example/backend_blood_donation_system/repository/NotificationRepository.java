package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
