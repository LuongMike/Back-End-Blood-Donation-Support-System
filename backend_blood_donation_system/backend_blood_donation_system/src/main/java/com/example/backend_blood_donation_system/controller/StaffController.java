package com.example.backend_blood_donation_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.DonationHistoryDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.service.AppointmentService;
import com.example.backend_blood_donation_system.service.DonationHistoryService;

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

    // ==== LỊCH SỬ HIẾN MÁU ====

    @GetMapping("/donation-history")
    public List<DonationHistoryDTO> getAllHistories() {
        return donationHistoryService.getAll();
    }

    @GetMapping("/donation-history/user/{userId}")
    public List<DonationHistoryDTO> getHistoriesByUser(@PathVariable Long userId) {
        return donationHistoryService.getByUserId(userId);
    }
}
