package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.BloodInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, BloodInventoryId> {
}