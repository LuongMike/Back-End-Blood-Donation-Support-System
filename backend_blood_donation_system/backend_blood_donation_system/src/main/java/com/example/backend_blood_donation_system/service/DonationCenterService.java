package com.example.backend_blood_donation_system.service;


import com.example.backend_blood_donation_system.dto.DonationCenterNameDTO;
import com.example.backend_blood_donation_system.entity.DonationCenter;
import com.example.backend_blood_donation_system.repository.DonationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationCenterService {

    @Autowired
    private DonationCenterRepository repository;

    public List<DonationCenterNameDTO> getAllDonationCenterNames() {
        List<DonationCenter> centers = repository.findAll();
        return centers.stream()
                .map(center -> new DonationCenterNameDTO(center.getName()))
                .collect(Collectors.toList());
    }
}
