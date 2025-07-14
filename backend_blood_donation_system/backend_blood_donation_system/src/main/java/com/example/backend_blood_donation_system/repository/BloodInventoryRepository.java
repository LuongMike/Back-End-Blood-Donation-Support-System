package com.example.backend_blood_donation_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.BloodInventoryId;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, BloodInventoryId> {

    // Tính tổng số đơn vị máu đã hiến ở dashboard staff 
    @Query("SELECT SUM(bi.unitsAvailable) FROM BloodInventory bi")
    Optional<Long> sumTotalUnitsAvailable();
    
}