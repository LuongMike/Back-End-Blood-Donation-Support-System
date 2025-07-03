package com.example.backend_blood_donation_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.DonationHistoryDTO;
import com.example.backend_blood_donation_system.dto.RecordDonationRequestDTO;
import com.example.backend_blood_donation_system.dto.ScreeningRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.service.AppointmentService;
import com.example.backend_blood_donation_system.service.DonationHistoryService;
import com.example.backend_blood_donation_system.service.BloodInventoryService;

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

}
