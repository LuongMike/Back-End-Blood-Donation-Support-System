package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    Optional<UserProfile> findByUser_UserId(int userId);

}
