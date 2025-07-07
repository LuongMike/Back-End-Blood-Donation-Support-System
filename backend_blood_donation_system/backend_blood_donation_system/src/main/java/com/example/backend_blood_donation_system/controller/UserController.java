package com.example.backend_blood_donation_system.controller;


import com.example.backend_blood_donation_system.dto.NotificationResponse;
import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.entity.UserNotification;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.NotificationService;

import com.example.backend_blood_donation_system.dto.BloodTypeDTO;
import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.dto.UserProfileDTO;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.service.BloodTypeService;

import com.example.backend_blood_donation_system.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired

    private NotificationService notificationService;

    private BloodTypeService bloodTypeService;


    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable int id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PatchMapping("/{id}/profile")

    public ResponseEntity<String> updateProfile(@PathVariable int id, @RequestBody UserProfileDTO dto) {
        userService.updateProfile(id, dto);
        return ResponseEntity.ok("Profile updated successfully");
    }
    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable int id) {
        UserInfoDTO userInfo = userService.getUserInfo(id);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getMyNotifications(@AuthenticationPrincipal CustomUserDetails user) {
        System.out.println("🔍 Logged in user ID = " + user.getId()); // in ra console
        List<NotificationResponse> notifications = notificationService.getUserNotifications(user.getId());
        return ResponseEntity.ok(notifications);
    }
    @PostMapping("/notification/{id}/accept")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<String> acceptNotification(@PathVariable("id") Integer notificationId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        notificationService.acceptDonationRequest(userDetails.getId(), notificationId);
        return ResponseEntity.ok("Bạn đã đồng ý hiến máu.");
    }

    @GetMapping("/blood_type")
    public ResponseEntity<List<BloodTypeDTO>> getAllBloodTypes() {
        List<BloodTypeDTO> bloodTypes = bloodTypeService.getAllBloodTypes();
        return ResponseEntity.ok(bloodTypes);

    }

}
