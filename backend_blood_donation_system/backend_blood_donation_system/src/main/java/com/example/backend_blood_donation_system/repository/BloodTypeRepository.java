package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.BloodType;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    // Có thể thêm method tìm theo loại máu nếu cần, ví dụ:
    BloodType findByType(String type);
}
