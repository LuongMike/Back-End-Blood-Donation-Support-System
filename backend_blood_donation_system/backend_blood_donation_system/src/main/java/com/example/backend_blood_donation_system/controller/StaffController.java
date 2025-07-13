package com.example.backend_blood_donation_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.AppointmentDTO;
import com.example.backend_blood_donation_system.dto.DashboardStatisticsDTO;
import com.example.backend_blood_donation_system.dto.DonationHistoryDTO;
import com.example.backend_blood_donation_system.dto.RecordDonationRequestDTO;
import com.example.backend_blood_donation_system.dto.ScreeningRequestDTO;
import com.example.backend_blood_donation_system.dto.UserProfileRequestDTO;
import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.service.AppointmentService;
import com.example.backend_blood_donation_system.service.BloodInventoryService;
import com.example.backend_blood_donation_system.service.DashboardService;
import com.example.backend_blood_donation_system.service.DonationHistoryService;
import com.example.backend_blood_donation_system.service.DonationRegistrationService;
import com.example.backend_blood_donation_system.service.UserProfileService;

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
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> dtos = appointmentService.getAllAppointmentDTOs();
        return ResponseEntity.ok(dtos);
    }

    // @GetMapping("/appointments/by-date")
    // public List<Appointment> getAppointmentsByDate(
    //         @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    //     return appointmentService.getAppointmentsByDate(date);
    // }

    @GetMapping("/appointments/by-date")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AppointmentDTO> dtos = appointmentService.getAppointmentDTOsByDate(date);
        return ResponseEntity.ok(dtos);
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
     * Body: { "appointmentId": ..., "bloodTypeId": ..., "componentTypeId": ...,
     * "units": ... }
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
     * API để bác sĩ/nhân viên cập nhật kho máu.
     * Endpoint: POST /api/staff/blood-inventory/update
     * Body: { "bloodTypeId": ..., "componentTypeId": ..., "quantity": ... }
     */
    @PostMapping("/blood-inventory/update")
    public ResponseEntity<BloodInventory> updateBloodInventory(@Valid @RequestBody BloodInventory inventory) {
        BloodInventory updatedInventory = bloodInventoryService.updateInventory(inventory);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }
    
    /**
     * API lấy toàn bộ dữ liệu kho máu.
     * GET /api/staff/blood-inventory
     */
    @GetMapping("/blood-inventory")
    public ResponseEntity<List<BloodInventory>> getBloodInventory() {
        List<BloodInventory> inventories = bloodInventoryService.getAllInventories();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
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

    // Lấy hồ sơ kiểm tra sức khỏe theo User ID
    @GetMapping("/user-profile/{userId}")
    public ResponseEntity<UserProfile> getUserProfileByUserId(@PathVariable Integer userId) {
        UserProfile userProfile = userProfileService.findProfileByUserId(userId);
        return ResponseEntity.ok(userProfile);
    }

     @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard/statistics")
    public ResponseEntity<DashboardStatisticsDTO> getStatistics() {
        DashboardStatisticsDTO statistics = dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(statistics);
    }
}
