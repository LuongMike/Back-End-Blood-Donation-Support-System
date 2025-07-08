package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.NearbyDonorDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.service.BloodSearchService;

@RestController
@RequestMapping("/api/blood-search")
public class BloodSearchController {

    @Autowired
    private BloodSearchService bloodSearchService;

    @GetMapping("/nearby-donors")
    @PreAuthorize("hasAuthority('STAFF')")
    public List<NearbyDonorDTO> getNearbyDonors(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radiusKm,
            @RequestParam(required = false) String bloodType
            ) {
        return bloodSearchService.findNearbyDonors(latitude, longitude, radiusKm, bloodType);
    }
}
