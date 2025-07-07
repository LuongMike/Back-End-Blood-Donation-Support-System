package com.example.backend_blood_donation_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.repository.DonationRegistrationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DonationRegistrationService {

    @Autowired
    private DonationRegistrationRepository registrationRepository;

    public void deleteRegistrationById(Integer registrationId) {
        if (!registrationRepository.existsById(registrationId)) {
            throw new EntityNotFoundException("Registration not found with id: " + registrationId);
        }
        registrationRepository.deleteById(registrationId);
    }
}
