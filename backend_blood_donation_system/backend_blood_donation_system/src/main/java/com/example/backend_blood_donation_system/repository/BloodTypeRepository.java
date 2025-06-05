package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BloodTypeRepository extends JpaRepository<BloodType,Integer> {

    Optional<BloodType> findByType(String type);

}
