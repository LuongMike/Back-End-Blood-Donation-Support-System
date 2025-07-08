package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.UserRegistrationDTO;
import com.example.backend_blood_donation_system.dto.UserResponseDTO;
import com.example.backend_blood_donation_system.service.AuthService;
import com.example.backend_blood_donation_system.service.JwtService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class    AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String login = loginRequest.getLogin();
        String password = loginRequest.getPassword();

        var userOpt = authService.login(login, password);
        if (userOpt.isPresent()) {
            UserResponseDTO user = userOpt.get();

            String token = jwtService.generateToken(
                    user.getUserId(),
                    user.getUsername(),
                    user.getRole()
            );

            return ResponseEntity.ok(new LoginResponse(true, "Đăng nhập thành công", token, user));
        } else {
            return ResponseEntity
                    .status(401)
                    .body(new LoginResponse(false, "Sai email hoặc mật khẩu hoặc tài khoản bị khóa", null, null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO dto) {
        var userOpt = authService.registerUser(dto);
        if (userOpt.isPresent()) {
            UserResponseDTO user = userOpt.get();

            String token = jwtService.generateToken(
                    user.getUserId(),
                    user.getUsername(),
                    user.getRole()
            );

            return ResponseEntity.ok(new LoginResponse(true, "Đăng ký thành công", token, user));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse(false, "Đăng ký thất bại", null, null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String tokenWithoutPrefix = token.startsWith("Bearer ") ? token.substring(7) : token;
        jwtService.blacklistToken(tokenWithoutPrefix);
        return ResponseEntity.ok("Đăng xuất thành công");
    }

    // ======== Inner DTOs ========

    @Data
    private static class LoginRequest {
        @NotBlank(message = "Login không được để trống")
        private String login;  // username hoặc email

        @NotBlank(message = "Mật khẩu không được để trống")
        private String password;
    }

    @Data
    @AllArgsConstructor
    private static class LoginResponse {
        private boolean success;
        private String message;
        private String token;
        private Object user;
    }
}
