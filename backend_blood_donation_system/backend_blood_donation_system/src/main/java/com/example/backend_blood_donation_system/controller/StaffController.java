package com.example.backend_blood_donation_system.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.backend_blood_donation_system.dto.*;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.entity.BloodInventory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DonationHistoryService donationHistoryService;

    // ==== LỊCH HẸN ====


    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }


    @GetMapping("/appointments/by-date")
    public List<Appointment> getAppointmentsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.getAppointmentsByDate(date);
    }

    // API sàng lọc dựa trên JSON Body
    // Ví dụ Postman request:
    // POST /api/staff/appointments/12/screening
    // Body (raw, JSON): { "passed": true, "remarks": "Tình trạng tốt" }
    @PostMapping("/appointments/{id}/screening")
    public ResponseEntity<String> performScreening(
            @PathVariable("id") Integer appointmentId,
            @RequestBody ScreeningRequestDTO request) {

        boolean updated = appointmentService.updateScreeningResult(
                appointmentId, request.isPassed(), request.getRemarks());

        if (updated) {
            return ResponseEntity.ok("Screening result updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
    }
    
    // ==== LỊCH SỬ HIẾN MÁU ====

    @GetMapping("/donation-history")
    public List<DonationHistoryDTO> getAllHistories() {
        return donationHistoryService.getAll();
    }

    @GetMapping("/donation-history/user/{userId}")
    public List<DonationHistoryDTO> getHistoriesByUser(@PathVariable Long userId) {
        return donationHistoryService.getByUserId(userId);
    }


    /**
     * API để bác sĩ/nhân viên ghi lại một ca hiến máu đã hoàn thành.
     * Endpoint: POST /api/staff/donation-history/record
     * Body: { "appointmentId": ..., "bloodTypeId": ..., "componentTypeId": ..., "units": ... }
     */
    @PostMapping("/donation-history/record")
    public ResponseEntity<DonationHistoryDTO> recordCompletedDonation(
            @Valid @RequestBody RecordDonationRequestDTO requestDTO) {
        
        DonationHistoryDTO createdDonationHistory = donationHistoryService.recordDonation(requestDTO);
        return new ResponseEntity<>(createdDonationHistory, HttpStatus.CREATED);
    }
    // ======================================================================
    @Autowired
    private BloodInventoryService bloodInventoryService;

    // ==== KHO MÁU ====

    /**
     * API lấy toàn bộ dữ liệu kho máu.
     * GET /api/staff/blood-inventory
     */
    @GetMapping("/blood-inventory")
    public ResponseEntity<List<BloodInventory>> getBloodInventory() {
        List<BloodInventory> inventories = bloodInventoryService.getAllInventories();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
    @PostMapping("/blood-inventory")
    public ResponseEntity<String> addOrUpdateInventory(@Valid @RequestBody BloodInventoryDTO dto) {
        bloodInventoryService.addOrUpdateInventory(dto);
        return ResponseEntity.ok("Inventory added or updated successfully.");
    }
    @Autowired
    private UserProfileService userProfileService;

    /**
     * Tạo mới hoặc cập nhật hồ sơ kiểm tra sức khỏe
     * POST /api/staff/user-profile
     */
    @PostMapping("/user-profile")
    public ResponseEntity<UserProfile> createOrUpdateUserProfile(@RequestBody UserProfileRequestDTO requestDTO) {
        UserProfile profile = userProfileService.createOrUpdate(requestDTO);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);



}
    @Autowired
    private DonationRegistrationService registrationService;



    // Hủy đơn đăng ký hiến máu
        @DeleteMapping("/registration/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Integer id) {
        registrationService.deleteRegistrationById(id);
        return ResponseEntity.ok("Deleted registration ID: " + id);
    }

    // Xóa hồ sơ kiểm tra sức khỏe
    @DeleteMapping("/user-profile/{userId}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable Integer userId) {
        userProfileService.deleteUserProfileByUserId(userId);
        return ResponseEntity.ok("Deleted user profile for user ID: " + userId);
    }
}
