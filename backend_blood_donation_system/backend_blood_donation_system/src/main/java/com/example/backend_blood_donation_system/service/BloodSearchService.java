package com.example.backend_blood_donation_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.NearbyDonorDTO;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.utils.DistanceUtil;

@Service
public class BloodSearchService {

    @Autowired
    private UserRepository userRepository;

    public List<NearbyDonorDTO> findNearbyDonors(double centerLat, double centerLng, double maxDistanceKm,
            String bloodType) {
        List<User> filteredDonors = userRepository.findAllByRole("MEMBER").stream()
                .filter(u -> u.getLatitude() != null && u.getLongitude() != null)
                .filter(u -> DistanceUtil.calculateDistance(centerLat, centerLng, u.getLatitude(),
                        u.getLongitude()) <= maxDistanceKm)
                .filter(u -> {
                    if (bloodType == null || bloodType.isBlank())
                        return true;
                    if (u.getProfile() == null || u.getProfile().getBloodType() == null)
                        return false;
                    String donorBloodType = u.getProfile().getBloodType().getType();
                    return donorBloodType != null && donorBloodType.trim().equalsIgnoreCase(bloodType.trim());
                })
                .collect(Collectors.toList());

        return filteredDonors.stream().map(user -> {
            NearbyDonorDTO dto = new NearbyDonorDTO();
            dto.setUserId(user.getUserId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());
            dto.setLatitude(user.getLatitude());
            dto.setLongitude(user.getLongitude());
            if (user.getProfile() != null && user.getProfile().getBloodType() != null) {
                dto.setBloodType(user.getProfile().getBloodType().getType());
            }
            return dto;
        }).collect(Collectors.toList());
    }

}
