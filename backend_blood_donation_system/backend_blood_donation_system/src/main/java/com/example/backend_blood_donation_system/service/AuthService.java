package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.UserRegistrationDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;



@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> login(String login, String password) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(login);
        if (userOpt.isPresent()) {
        User user = userOpt.get();
        // Check status active (tùy quy ước chuỗi của bạn, có thể là "Active" hoặc "ACTIVE")
        if (!"Active".equalsIgnoreCase(user.getStatus().trim())) {
            // Có thể trả về Optional.empty() hoặc throw exception custom (bị khóa)
            return Optional.empty();
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }
    }
    return Optional.empty();
}




    public String registerUser(UserRegistrationDTO dto) {
    // Kiểm tra confirm password
    if (!dto.getPassword().equals(dto.getConfirmPassword())) {
        return "Password confirmation does not match!";
    }

    // Kiểm tra tồn tại username
    if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
        return "Username already exists!";
    }

    // Kiểm tra tồn tại email
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
        return "Email already exists!";
    }
    //Mã hóa mật khẩu
    String encodedPassword = passwordEncoder.encode(dto.getPassword());

    // Tạo user mới
    User newUser = User.builder()
            .username(dto.getUsername())
            .fullName(dto.getFullName())
            .email(dto.getEmail())
            .password(encodedPassword)  
            .phoneNumber(dto.getPhoneNumber())
            .gender(dto.getGender())      
            .address(dto.getAddress())    
            .role("MEMBER")      
            .createdAt(LocalDateTime.now())
            .status("Active") 
            .build();

    userRepository.save(newUser);

    return "User registered successfully!";
}

}