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

import com.example.backend_blood_donation_system.dto.ScreeningRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.service.AppointmentService;


@RestController
@RequestMapping("/api/staff") // Tất cả các API trong controller này sẽ bắt đầu bằng /api/staff
public class StaffController {
     @Autowired
    private AppointmentService appointmentService;

    // API để lấy tất cả các cuộc hẹn
    // URL đầy đủ sẽ là: GET /api/staff/appointments
    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // API để lấy các cuộc hẹn theo ngày
    // Ví dụ: GET /api/staff/appointments/by-date")
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
}