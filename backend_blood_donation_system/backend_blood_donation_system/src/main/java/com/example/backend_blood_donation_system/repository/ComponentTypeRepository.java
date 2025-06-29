package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.ComponentType;

@Repository
public interface ComponentTypeRepository extends JpaRepository<ComponentType, Long> {
    // Spring Data JPA sẽ tự động cung cấp các phương thức CRUD cơ bản
}