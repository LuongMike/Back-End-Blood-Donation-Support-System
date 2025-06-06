package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.UserAdminDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;


@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //lấy danh sách người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //lấy thông tin người dùng theo id
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    //tạo người dùng mới
public Optional<User> createUser(UserAdminDTO dto) {
    // Kiểm tra username hoặc email đã tồn tại chưa
    if (userRepository.existsByUsername(dto.getUsername()) || userRepository.existsByEmail(dto.getEmail())) {
        return Optional.empty();
    }
    User user = User.builder()
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .fullName(dto.getFullName())
            .email(dto.getEmail())
            .gender(dto.getGender())
            .role(dto.getRole())
            .status("Active")
            .address(dto.getAddress())
            .phoneNumber(dto.getPhoneNumber())
            .createdAt(LocalDateTime.now())
            .build();
    return Optional.of(userRepository.save(user));
}

    //Đổi role người dùng
    public Optional<User> updateUserRole(Integer id, String newRole) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setRole(newRole);
            userRepository.save(user);
        });
        return userOpt;
    }

    //vô hiệu hóa người dùng//kích hoạt người dùng
    public Optional<User> updateUserStatus(Integer id, String status) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setStatus(status);
            userRepository.save(user);
        });
        return userOpt;
    }

    //câp nhật thông tin người dùng
    public Optional<User> updateUser(Integer id, UserAdminDTO dto) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setAddress(dto.getAddress());
            user.setPhoneNumber(dto.getPhoneNumber());
            userRepository.save(user);
        });
        return userOpt;
    }

    //xóa người dùng
    public boolean deleteUser(Integer id) {
    if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return true;
    } else {
        return false;
    }
}


}