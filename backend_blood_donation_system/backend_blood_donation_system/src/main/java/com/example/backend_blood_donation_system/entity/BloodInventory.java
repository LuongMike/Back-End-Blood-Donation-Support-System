package com.example.backend_blood_donation_system.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.EmbeddedId; // Import tất cả từ jakarta.persistence
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Entity
@Data
public class BloodInventory {
    
    @EmbeddedId
    private BloodInventoryId id; // Sử dụng khóa chính phức hợp

    // SỬA ĐỔI: Thêm các mối quan hệ để làm rõ dữ liệu
    // @MapsId cho phép chúng ta ánh xạ các trường trong khóa phức hợp (id)
    // tới các cột khóa ngoại trong chính bảng này.
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("centerId") // Ánh xạ tới trường 'centerId' trong BloodInventoryId
    @JoinColumn(name = "center_id")
    private DonationCenter center;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bloodTypeId") // Ánh xạ tới trường 'bloodTypeId' trong BloodInventoryId
    @JoinColumn(name = "blood_type_id")
    private BloodType bloodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("componentTypeId") // Ánh xạ tới trường 'componentTypeId' trong BloodInventoryId
    @JoinColumn(name = "component_type_id")
    private ComponentType componentType;
    
    // Các trường còn lại giữ nguyên
    private Integer unitsAvailable;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}