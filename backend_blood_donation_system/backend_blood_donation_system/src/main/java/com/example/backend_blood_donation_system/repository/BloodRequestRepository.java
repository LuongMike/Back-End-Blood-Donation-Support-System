package com.example.backend_blood_donation_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_blood_donation_system.entity.BloodRequest;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
    
    List<BloodRequest> findByStatus(String status);
    
    @Query("SELECT br FROM BloodRequest br WHERE br.requester.userId = :userId")
    List<BloodRequest> findByRequesterId(@Param("userId") Integer userId);
    
    @Query("SELECT br FROM BloodRequest br WHERE br.bloodType = :bloodType AND br.status = 'PENDING'")
    List<BloodRequest> findPendingRequestsByBloodType(@Param("bloodType") String bloodType);
    
    @Query("SELECT br FROM BloodRequest br WHERE br.status = 'PENDING' ORDER BY br.requiredDate ASC")
    List<BloodRequest> findPendingRequestsOrderByRequiredDate();
} 