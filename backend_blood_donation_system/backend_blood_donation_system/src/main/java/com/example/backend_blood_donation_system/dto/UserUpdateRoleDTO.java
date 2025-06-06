package com.example.backend_blood_donation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateRoleDTO {
    @NotBlank(message = "Vai trò không được để trống")
    @Pattern(regexp = "^(Admin|User|Staff)$", message = "Vai trò phải là 'Admin', 'User' hoặc 'Staff'")
    private String role; // "Admin", "User", "Staff"
}
