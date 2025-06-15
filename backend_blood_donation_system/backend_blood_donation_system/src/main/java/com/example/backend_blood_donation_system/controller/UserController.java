package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.UserInfoDTO;
import com.example.backend_blood_donation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable int id) {
        UserInfoDTO userInfo = userService.getUserInfo(id);
        return ResponseEntity.ok(userInfo);
    }
}
