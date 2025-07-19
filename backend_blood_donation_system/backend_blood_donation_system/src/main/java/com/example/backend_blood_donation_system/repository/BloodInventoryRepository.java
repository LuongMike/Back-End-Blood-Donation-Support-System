package com.example.backend_blood_donation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.BloodInventoryId;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, BloodInventoryId> {
    
}