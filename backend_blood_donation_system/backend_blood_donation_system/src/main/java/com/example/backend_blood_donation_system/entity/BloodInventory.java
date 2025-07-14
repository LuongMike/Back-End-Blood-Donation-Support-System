package com.example.backend_blood_donation_system.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class BloodInventory {
    
    @EmbeddedId
    private BloodInventoryId id; // Sử dụng khóa chính phức hợp

    private Integer unitsAvailable;

    @UpdateTimestamp // Tự động cập nhật thời gian mỗi khi bản ghi được sửa đổi
    private LocalDateTime updatedAt;

}
