package com.example.backend_blood_donation_system.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend_blood_donation_system.entity.UserProfile;
import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
}
