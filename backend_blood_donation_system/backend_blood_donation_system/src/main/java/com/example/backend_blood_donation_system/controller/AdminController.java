package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.service.BloodInventoryService;

@RestController
@RequestMapping("/api/admin")

public class AdminController {
    @Autowired
    private BloodInventoryService bloodInventoryService;
    /**
     * API lấy toàn bộ dữ liệu kho máu.
     * GET /api/admin/blood-inventory
     */
    @GetMapping("/blood-inventory")
    public ResponseEntity<List<BloodInventory>> getBloodInventory() {
        List<BloodInventory> inventories = bloodInventoryService.getAllInventories();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
}
