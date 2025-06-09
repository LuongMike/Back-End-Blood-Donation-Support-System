package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.BloodRequestDTO;
import com.example.backend_blood_donation_system.service.BloodRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/staff/blood-requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @GetMapping
    public ResponseEntity<List<BloodRequestDTO>> getAllBloodRequests() {
        return ResponseEntity.ok(bloodRequestService.getAllBloodRequests());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BloodRequestDTO>> getPendingBloodRequests() {
        return ResponseEntity.ok(bloodRequestService.getPendingBloodRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequestDTO> getBloodRequestById(@PathVariable Integer id) {
        BloodRequestDTO request = bloodRequestService.getBloodRequestById(id);
        if (request != null) {
            return ResponseEntity.ok(request);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BloodRequestDTO> createBloodRequest(@Valid @RequestBody BloodRequestDTO dto) {
        BloodRequestDTO created = bloodRequestService.createBloodRequest(dto);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BloodRequestDTO> updateBloodRequestStatus(
            @PathVariable Integer id,
            @RequestBody String status) {
        BloodRequestDTO updated = bloodRequestService.updateBloodRequestStatus(id, status);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
} 