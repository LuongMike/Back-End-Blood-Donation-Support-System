package com.example.backend_blood_donation_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.utils.DistanceUtil;

@Service
public class BloodSearchService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findNearbyDonors(double centerLat, double centerLng, double maxDistanceKm, String bloodType) {
        List<User> allDonors = userRepository.findAllByRole("DONOR");

        return allDonors.stream()
                .filter(u -> u.getLatitude() != null && u.getLongitude() != null)
                .filter(u -> DistanceUtil.calculateDistance(centerLat, centerLng, u.getLatitude(),
                        u.getLongitude()) <= maxDistanceKm)
                
                .collect(Collectors.toList());
    }
}
