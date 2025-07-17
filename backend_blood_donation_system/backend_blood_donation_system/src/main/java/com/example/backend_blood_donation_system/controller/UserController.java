package com.example.backend_blood_donation_system.controller;


import com.example.backend_blood_donation_system.dto.*;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.entity.UserNotification;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.*;

import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.repository.UserRepository;

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

    @Autowired
    private DonationHistoryService donationHistoryService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUser().getUserId();
        UserProfileDTO dto = userService.getProfile(userId);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UserProfileDTO dto) {

        int userId = userDetails.getUser().getUserId();
        userService.updateProfile(userId, dto);
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

    @GetMapping("/donation-history")
    public ResponseEntity<List<DonationHistoryDTO>> getDonationHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Integer userId = userDetails.getUser().getUserId();
        List<DonationHistoryDTO> dtoList = donationHistoryService.getDonationHistoryByUser(userId);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Integer userId = userDetails.getUser().getUserId();
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(appointments);
    }

}
