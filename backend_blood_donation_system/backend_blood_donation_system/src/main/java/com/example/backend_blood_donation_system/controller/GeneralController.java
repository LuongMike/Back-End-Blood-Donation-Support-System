package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.entity.ComponentType;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import com.example.backend_blood_donation_system.repository.ComponentTypeRepository;

@RestController
@RequestMapping("/api")
public class GeneralController {
     @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    /**
     * API để lấy danh sách tất cả các loại máu.
     * Endpoint: GET /api/blood-types
     * @return Danh sách các BloodType.
     */
    @GetMapping("/blood-types")
    public List<BloodType> getAllBloodTypes() {
        return bloodTypeRepository.findAll();
    }

    /**
     * API để lấy danh sách tất cả các loại thành phần máu.
     * Endpoint: GET /api/component-types
     * @return Danh sách các ComponentType.
     */
    @GetMapping("/component-types")
    public List<ComponentType> getAllComponentTypes() {
        return componentTypeRepository.findAll();
    }
}
