package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.BloodType;

public interface  BloodTypeRepository extends JpaRepository<BloodType, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
    // Ví dụ: tìm kiếm theo tên loại máu
    
}
