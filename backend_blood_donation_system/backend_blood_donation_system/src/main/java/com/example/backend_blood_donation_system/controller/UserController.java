package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.dto.UserProfileDTO;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable int id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<String> updateProfile(@PathVariable int id, @RequestBody UserProfileDTO dto) {
        userService.updateProfile(id, dto);
        return ResponseEntity.ok("Profile updated successfully");
    }
    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable int id) {
        UserInfoDTO userInfo = userService.getUserInfo(id);
        return ResponseEntity.ok(userInfo);
    }

}
