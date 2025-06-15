package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.UserProfileResponse;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserProfileResponse getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfileResponse dto = new UserProfileResponse();
        dto.setFullName(user.getFullName());
        dto.setDateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null);
        dto.setGender(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        return dto;
    }
} 