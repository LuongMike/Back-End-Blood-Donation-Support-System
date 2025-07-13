package com.example.backend_blood_donation_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.UserProfileRequestDTO;
import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import com.example.backend_blood_donation_system.repository.UserProfileRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Transactional
    public UserProfile createOrUpdate(UserProfileRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BloodType bloodType = bloodTypeRepository.findById(dto.getBloodTypeId())
                .orElseThrow(() -> new RuntimeException("Blood type not found"));

        Optional<UserProfile> existingProfileOpt = userProfileRepository.findByUser_UserId(dto.getUserId());

        UserProfile profile = existingProfileOpt.orElse(new UserProfile());
        profile.setUser(user);
        profile.setBloodType(bloodType);
        profile.setWeight(dto.getWeight());
        profile.setLastScreeningDate(dto.getLastScreeningDate());
        profile.setHealthStatus(dto.getHealthStatus());

        return userProfileRepository.save(profile);
    }


    @Transactional
    public void deleteUserProfileByUserId(Integer userId) {
        if (!userProfileRepository.existsByUser_UserId(userId)) {
            throw new EntityNotFoundException("User profile not found for user ID: " + userId);
        }
        userProfileRepository.deleteByUser_UserId(userId);
    }

    public UserProfile findProfileByUserId(Integer userId) {

        return userProfileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for user ID: " + userId));
    }
}

