package com.example.backend_blood_donation_system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend_blood_donation_system.dto.ChartDataDTO;
import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.entity.User;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
     @Query("SELECT r FROM BloodRequest r WHERE r.requester.userId = :userId")
     List<BloodRequest> findByRequesterUserId(@Param("userId") Integer userId);

     List<BloodRequest> findByAssignedStaff(User staff);

     List<BloodRequest> findByAssignedStaffUserId(Integer staffId);


    // MỚI: Đếm các yêu cầu khẩn cấp và chưa hoàn thành hoặc bị từ chối
    @Query("SELECT COUNT(br) FROM BloodRequest br WHERE br.type = 'URGENT' AND br.status NOT IN ('COMPLETED', 'REJECTED')")
    long countUrgentAndActiveRequests();


     long countByStatus(BloodRequest.RequestStatus status);

     @Query("SELECT new com.example.backend_blood_donation_system.dto.ChartDataDTO(" +
           "FORMAT(br.requestTime, 'yyyy-MM'), " +      // <-- Sửa tên trường
           "COUNT(br.id)) " +
           "FROM BloodRequest br " +
           "WHERE br.requestTime >= :startDate " +     // <-- Sửa tên trường
           "GROUP BY FORMAT(br.requestTime, 'yyyy-MM') " + // <-- Sửa tên trường
           "ORDER BY FORMAT(br.requestTime, 'yyyy-MM') ASC") // <-- Sửa tên trường
    List<ChartDataDTO> countRequestsByMonth(@Param("startDate") LocalDateTime startDate);


    // MỚI: Đếm các yêu cầu bình thường và chưa hoàn thành hoặc bị từ chối
    @Query("SELECT COUNT(br) FROM BloodRequest br WHERE br.type = 'NORMAL' AND br.status NOT IN ('COMPLETED', 'REJECTED')")
    long countNormalAndActiveRequests();

}
