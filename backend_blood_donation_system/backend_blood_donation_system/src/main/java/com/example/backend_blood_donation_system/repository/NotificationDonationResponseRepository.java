package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.Notification;
import com.example.backend_blood_donation_system.entity.NotificationDonationResponse;
import com.example.backend_blood_donation_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationDonationResponseRepository extends JpaRepository<NotificationDonationResponse, Integer> {
    boolean existsByUserAndNotification(User user, Notification notification);
    List<NotificationDonationResponse> findByNotification(Notification notification);
}
