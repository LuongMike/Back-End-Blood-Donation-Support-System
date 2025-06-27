package com.example.backend_blood_donation_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.entity.User;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
     @Query("SELECT r FROM BloodRequest r WHERE r.requester.userId = :userId")
     List<BloodRequest> findByRequesterUserId(@Param("userId") Integer userId);

     List<BloodRequest> findByAssignedStaff(User staff);

     List<BloodRequest> findByAssignedStaffUserId(Integer staffId);

}
