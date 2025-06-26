package com.example.backend_blood_donation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserStatusDTO {
    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(active|inactive|banned)$", message = "Trạng thái phải là 'active', 'inactive' hoặc 'banned'")
    private String status; // "active", "inactive", "banned"
}
