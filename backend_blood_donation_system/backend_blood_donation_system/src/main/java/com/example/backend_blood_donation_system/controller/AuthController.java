package com.example.backend_blood_donation_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getLogin(), loginRequest.getPassword())
                .map(user -> ResponseEntity.ok(new LoginResponse(true, "Đăng nhập thành công", user.getFullName())))
                .orElseGet(() -> ResponseEntity.status(401).body(new LoginResponse(false, "Sai email hoặc mật khẩu", null)));
    }

    @Data
    private static class LoginRequest {
        private String login;      // username hoặc email
        private String password;
    }

    @Data
    @AllArgsConstructor
    private static class LoginResponse {
        private boolean success;
        private String message;
        private String fullName;   // tên người dùng trả về nếu login thành công
    }
}
    