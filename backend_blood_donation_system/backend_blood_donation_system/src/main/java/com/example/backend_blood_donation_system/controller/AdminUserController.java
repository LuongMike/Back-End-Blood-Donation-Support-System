package com.example.backend_blood_donation_system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.UserAdminDTO;
import com.example.backend_blood_donation_system.dto.UserAdminResponseDTO;
import com.example.backend_blood_donation_system.dto.UserStatusDTO;
import com.example.backend_blood_donation_system.dto.UserUpdateRoleDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.service.AdminUserService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // lấy danh sách người dùng
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = adminUserService.getAllUsers();

        List<UserAdminResponseDTO> userDTOs = users.stream().map(user -> new UserAdminResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getAddress(),
                user.getRole(),
                user.getStatus())).toList();

        return ResponseEntity.ok(new AdminResponse(true, "Lấy danh sách user thành công!", userDTOs));
    }

    // lấy thông tin người dùng theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Optional<User> userOpt = adminUserService.getUserById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new AdminResponse(true, "Lấy user thành công!", userOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new AdminResponse(false, "Không tìm thấy user!", null));
        }
    }

    // Tạo người dùng mới
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserAdminDTO dto) {
        Optional<User> created = adminUserService.createUser(dto);
        if (created.isPresent()) {
            return ResponseEntity.status(201).body(
                    new AdminResponse(true, "Tạo tài khoản thành công!", created.get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new AdminResponse(false, "Username hoặc Email đã tồn tại!", null));
        }
    }

    // đôi role người dùng
    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody UserUpdateRoleDTO dto) {
        Optional<User> userOpt = adminUserService.updateUserRole(id, dto.getRole());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new AdminResponse(true, "Cập nhật vai trò thành công!", userOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new AdminResponse(false, "Không tìm thấy user!", null));
        }
    }

    // vô hiệu hóa người dùng
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody UserStatusDTO dto) {
        Optional<User> userOpt = adminUserService.updateUserStatus(id, dto.getStatus());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new AdminResponse(true, "Cập nhật trạng thái thành công!", userOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new AdminResponse(false, "Không tìm thấy user!", null));
        }
    }

    // sửa thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserAdminDTO dto) {
        Optional<User> userOpt = adminUserService.updateUser(id, dto);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new AdminResponse(true, "Cập nhật thông tin thành công!", userOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new AdminResponse(false, "Không tìm thấy user!", null));
        }
    }

    // xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.ok(new AdminResponse(true, "Xóa tài khoản thành công!", null));
    }

    @Data
    @AllArgsConstructor
    private static class AdminResponse {
        private boolean success;
        private String message;
        private Object data;

    }
}
