package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    Optional<UserProfile> findByUser_UserId(int userId);
    @Query("SELECT p FROM UserProfile p WHERE p.user.userId = :userId")
    Optional<UserProfile> findByUserId(Integer userId);

}
