package com.example.loginapi.controller;
import com.example.loginapi.model.Users;
import com.example.loginapi.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/google-login")
    public Users googleLogin(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        try {
            return authService.verifyGoogleTokenAndSaveUser(idToken);
        } catch (Exception e) {
            throw new RuntimeException("Đăng nhập Google thất bại: " + e.getMessage());
        }
    }

}
