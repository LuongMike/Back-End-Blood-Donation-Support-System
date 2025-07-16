package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.UserRegistrationDTO;
import com.example.backend_blood_donation_system.dto.UserResponseDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GeocodingService geocodingService;

    public Optional<UserResponseDTO> login(String login, String password) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(login);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!"Active".equalsIgnoreCase(user.getStatus().trim())) {
                // Có thể trả về Optional.empty() hoặc throw exception custom (bị khóa)
                return Optional.empty();
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(convertToDTO(user));
            }
        }
        return Optional.empty();
    }

    public Optional<UserResponseDTO> registerUser(UserRegistrationDTO dto) {
        // Kiểm tra confirm password
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return Optional.empty();
        }

        // Kiểm tra tồn tại username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return Optional.empty();
        }

        // Kiểm tra tồn tại email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return Optional.empty();
        }
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        GeocodingService.Coordinates coordinates = geocodingService.getCoordinatesFromAddress(dto.getAddress());

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
                .latitude(coordinates != null ? coordinates.getLatitude() : null)
                .longitude(coordinates != null ? coordinates.getLongitude() : null)
                .build();

        userRepository.save(newUser);

        return Optional.of(convertToDTO(newUser));
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

}