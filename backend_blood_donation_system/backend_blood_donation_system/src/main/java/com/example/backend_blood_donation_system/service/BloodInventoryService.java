package com.example.backend_blood_donation_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;

@Service
public class BloodInventoryService {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    

    public List<BloodInventory> getAllInventories() {
        return bloodInventoryRepository.findAll();
    }

    public BloodInventory updateInventory(BloodInventory inventory) {
        return bloodInventoryRepository.save(inventory);
    }
}

