package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodInventoryService {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public List<BloodInventory> getAllInventories() {
        return bloodInventoryRepository.findAll();
    }

}

