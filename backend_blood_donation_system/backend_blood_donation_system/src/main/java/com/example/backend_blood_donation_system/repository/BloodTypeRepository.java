package com.example.backend_blood_donation_system.repository;

//<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.backend_blood_donation_system.entity.BloodType;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    // Có thể thêm method tìm theo loại máu nếu cần, ví dụ:
    Optional<BloodType> findByType(String type);
//=======
//import com.example.backend_blood_donation_system.entity.BloodType;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface BloodTypeRepository extends JpaRepository<BloodType,Long> {
//
//    Optional<BloodType> findByType(String type);
//
//>>>>>>> Feature-View-User
}
