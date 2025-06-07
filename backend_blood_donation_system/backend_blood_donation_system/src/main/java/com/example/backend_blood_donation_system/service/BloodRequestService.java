package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.BloodRequestDTO;
import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.BloodRequestRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

@Service
public class BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public List<BloodRequestDTO> getAllBloodRequests() {
        return bloodRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BloodRequestDTO> getPendingBloodRequests() {
        return bloodRequestRepository.findByStatus("PENDING").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BloodRequestDTO getBloodRequestById(Integer id) {
        Optional<BloodRequest> request = bloodRequestRepository.findById(id);
        return request.map(this::convertToDTO).orElse(null);
    }

    public BloodRequestDTO updateBloodRequestStatus(Integer id, String status) {
        Optional<BloodRequest> requestOpt = bloodRequestRepository.findById(id);
        if (requestOpt.isPresent()) {
            BloodRequest request = requestOpt.get();
            request.setStatus(status);
            request.setUpdatedAt(LocalDateTime.now());
            return convertToDTO(bloodRequestRepository.save(request));
        }
        return null;
    }

    public BloodRequestDTO createBloodRequest(BloodRequestDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getRequesterId());
        if (userOpt.isPresent()) {
            BloodRequest request = new BloodRequest();
            request.setRequester(userOpt.get());
            request.setBloodType(dto.getBloodType());
            request.setQuantity(dto.getQuantity());
            request.setHospitalName(dto.getHospitalName());
            request.setHospitalAddress(dto.getHospitalAddress());
            request.setReason(dto.getReason());
            request.setStatus("PENDING");
            request.setCreatedAt(LocalDateTime.now());
            request.setRequiredDate(dto.getRequiredDate());
            request.setNotes(dto.getNotes());

            return convertToDTO(bloodRequestRepository.save(request));
        }
        return null;
    }

    private BloodRequestDTO convertToDTO(BloodRequest request) {
        BloodRequestDTO dto = new BloodRequestDTO();
        dto.setRequestId(request.getRequestId());
        dto.setBloodType(request.getBloodType());
        dto.setQuantity(request.getQuantity());
        dto.setHospitalName(request.getHospitalName());
        dto.setHospitalAddress(request.getHospitalAddress());
        dto.setReason(request.getReason());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setRequiredDate(request.getRequiredDate());
        dto.setNotes(request.getNotes());

        if (request.getRequester() != null) {
            dto.setRequesterId(request.getRequester().getUserId());
            dto.setRequesterName(request.getRequester().getFullName());
            dto.setRequesterEmail(request.getRequester().getEmail());
            dto.setRequesterPhone(request.getRequester().getPhoneNumber());
        }

        return dto;
    }
} 