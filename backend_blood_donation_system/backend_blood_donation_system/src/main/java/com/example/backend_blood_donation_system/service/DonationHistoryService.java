package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.DonationHistoryDTO;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public List<DonationHistoryDTO> getAll() {
        return donationHistoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DonationHistoryDTO> getByUserId(Long userId) {
        return donationHistoryRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DonationHistoryDTO convertToDTO(DonationHistory history) {
        DonationHistoryDTO dto = new DonationHistoryDTO();
        dto.setDonationId(history.getDonationId());
        dto.setUserFullName(history.getUser().getFullName());
        dto.setCenterName(history.getCenter().getName());
        dto.setBloodType(history.getBloodType().getType());
        dto.setComponentType(history.getComponentType().getName());
        dto.setDonationDate(history.getDonationDate());
        dto.setVolumeMl(history.getVolumeMl());
        return dto;
    }
}

