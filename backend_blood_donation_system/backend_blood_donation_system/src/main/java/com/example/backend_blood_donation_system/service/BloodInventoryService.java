package com.example.backend_blood_donation_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.BloodInventoryDTO;
import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.BloodInventoryId;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;

@Service
public class BloodInventoryService {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public List<BloodInventory> getAllInventories() {
        return bloodInventoryRepository.findAll();
    }

    public void addOrUpdateInventory(BloodInventoryDTO dto) {
        BloodInventoryId id = new BloodInventoryId(
                dto.getCenterId(),
                dto.getBloodTypeId(),
                dto.getComponentTypeId()
        );

        BloodInventory inventory = bloodInventoryRepository.findById(id)
                .orElse(new BloodInventory());

        inventory.setId(id);
        inventory.setUnitsAvailable(dto.getUnitsAvailable());

        bloodInventoryRepository.save(inventory);
    }
}

