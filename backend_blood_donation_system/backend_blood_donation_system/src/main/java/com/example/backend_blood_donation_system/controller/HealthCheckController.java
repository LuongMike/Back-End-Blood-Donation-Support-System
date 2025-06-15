package com.example.backend_blood_donation_system.controller;
import com.example.backend_blood_donation_system.dto.HealthCheckRequest;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/staff/Health-check")

public class HealthCheckController {
    @Autowired
    private HealthCheckService healthCheckService;
    // Tạo mới hồ sơ sức khỏe
    @PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody HealthCheckRequest request) {
        try {
            UserProfile created = healthCheckService.createHealthCheck(request);
            return ResponseEntity.ok(created);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // Cập nhật hồ sơ sức khỏe theo userId
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> update(@PathVariable Long userId, @RequestBody HealthCheckRequest request) {
        try {
            UserProfile updated = healthCheckService.updateHealthCheck(userId, request);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
