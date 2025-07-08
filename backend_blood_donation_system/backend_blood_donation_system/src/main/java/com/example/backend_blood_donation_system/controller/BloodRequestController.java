package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.BloodRequestDTO;
import com.example.backend_blood_donation_system.dto.StaffDTO;
import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.entity.BloodRequest.RequestStatus;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.BloodRequestService;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('TREATMENT_CENTER')")
    public ResponseEntity<?> createRequest(@AuthenticationPrincipal CustomUserDetails user,
            @RequestBody BloodRequestDTO dto) {
        return ResponseEntity.ok(bloodRequestService.createRequest(dto, user.getId()));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAuthority('TREATMENT_CENTER')")
    public ResponseEntity<?> getOwnRequests(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByRequester(user.getId()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok(bloodRequestService.getAllRequests());
    }

    @GetMapping("/staffs")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllStaffs() {
        List<User> staffs = userRepository.findByRole("STAFF");
    
    // Chuyển sang DTO để trả dữ liệu gọn và chuẩn
    List<StaffDTO> staffDTOs = staffs.stream()
        .map(user -> new StaffDTO(user.getUserId(), user.getFullName()))
        .toList();

    return ResponseEntity.ok(staffDTOs);
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> assignStaffToRequest(
            @PathVariable Integer id,
            @RequestParam("status") String status,
            @RequestParam("staffId") Integer staffId) {
        return ResponseEntity.ok(bloodRequestService.assignStaff(id, RequestStatus.valueOf(status), staffId));
    }

    @GetMapping("/assigned")
    @PreAuthorize("hasAuthority('STAFF')")
    public ResponseEntity<?> getAssignedRequests(@AuthenticationPrincipal CustomUserDetails staff) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByStaff(staff.getId()));
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public ResponseEntity<?> updateRequestStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        System.out.println("➡️ API được gọi với status = " + status);
        try {
            BloodRequest.RequestStatus newStatus = BloodRequest.RequestStatus.valueOf(status.toUpperCase());
            BloodRequest updated = bloodRequestService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.err.println("❌ Lỗi cập nhật trạng thái: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

}
