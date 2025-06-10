package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.UserProfileResponse;
import com.example.backend_blood_donation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff/profile")
public class StaffProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    public UserProfileResponse getProfile(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserProfileByUsername(username);
    }
} 