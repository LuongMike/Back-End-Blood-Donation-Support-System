package com.example.backend_blood_donation_system.controller;


import com.example.backend_blood_donation_system.dto.NotificationRequest;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // API để STAFF gửi thông báo broadcast tới tất cả donor
    @PostMapping("/broadcast")
    @PreAuthorize("hasAuthority('STAFF')")
    public ResponseEntity<String> sendBroadcast(@RequestBody NotificationRequest request,@AuthenticationPrincipal CustomUserDetails staffDetails) {
        Integer staffId = staffDetails.getId();
        notificationService.createNotification(request,staffId);
        return ResponseEntity.ok("Thông báo đã được gửi đến tất cả donor.");
    }


}
