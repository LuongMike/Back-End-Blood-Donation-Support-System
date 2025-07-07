package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.ComponentType;

@Repository

public interface ComponentTypeRepository extends JpaRepository<ComponentType, Integer> {
    // Có thể thêm phương thức truy vấn tùy chỉnh nếu cần
    ComponentType findByName(String name);

}