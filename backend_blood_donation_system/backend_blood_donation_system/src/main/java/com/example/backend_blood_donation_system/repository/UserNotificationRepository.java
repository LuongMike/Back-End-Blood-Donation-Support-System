package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    List<UserNotification> findByUser_UserId(Integer userId);
}