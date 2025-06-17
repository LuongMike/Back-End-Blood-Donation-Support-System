package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.DonationCenterNameDTO;
import com.example.backend_blood_donation_system.service.DonationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/donation-center")
public class DonationCenterController {
    @Autowired
    private DonationCenterService service;

    @GetMapping("/names")
    public List<DonationCenterNameDTO> getAllDonationCenterNames() {
        return service.getAllDonationCenterNames();
    }
}
