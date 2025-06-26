package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.dto.UserProfileDTO;
import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import com.example.backend_blood_donation_system.repository.UserProfileRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserProfileRepository userProfileRepository;
    @Autowired private BloodTypeRepository bloodTypeRepository;
    public UserProfileDTO getProfile(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = user.getProfile();
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setGender(user.getGender());

        if (profile != null) {
            dto.setWeight(profile.getWeight());
            dto.setBlood_type(profile.getBloodType().getType());
            dto.setHealthStatus(profile.getHealth_status());
        }

        return dto;
    }

    public void updateProfile(int userId, UserProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật thông tin user
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setGender(dto.getGender());
        userRepository.save(user);

        // Cập nhật profile
        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }

        profile.setWeight(dto.getWeight());
        profile.setHealth_status(dto.getHealthStatus());

        if (dto.getBlood_type() != null) {
            BloodType bloodType = bloodTypeRepository.findByType(dto.getBlood_type())
                    .orElseThrow(() -> new RuntimeException("Invalid blood type"));
            profile.setBloodType(bloodType);
        }

        userProfileRepository.save(profile);
    }
    public UserInfoDTO getUserInfo(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return new UserInfoDTO(
                user.getUsername(),
                user.getGender(),
                user.getPhoneNumber()
        );
    }

}
