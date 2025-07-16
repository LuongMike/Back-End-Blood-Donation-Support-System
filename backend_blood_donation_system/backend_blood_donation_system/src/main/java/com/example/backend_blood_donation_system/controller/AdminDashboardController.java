package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.AdminDashboardDTO;
import com.example.backend_blood_donation_system.dto.ChartDataDTO;
import com.example.backend_blood_donation_system.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public AdminDashboardDTO getAdminDashboard() {
        return adminDashboardService.getDashboardData();
    }

    @GetMapping("/donations-by-month")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ChartDataDTO> getDonationsByMonth() {
        return adminDashboardService.getMonthlyDonationsChartData();
    }

    @GetMapping("/requests-by-month")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ChartDataDTO> getRequestsByMonth() {
        return adminDashboardService.getMonthlyRequestsChartData();
    }
}