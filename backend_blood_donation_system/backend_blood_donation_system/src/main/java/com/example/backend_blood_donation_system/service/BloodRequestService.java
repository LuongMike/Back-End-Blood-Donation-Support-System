package com.example.backend_blood_donation_system.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.BloodRequestDTO;
import com.example.backend_blood_donation_system.entity.BloodRequest;
import com.example.backend_blood_donation_system.entity.BloodRequest.RequestStatus;
import com.example.backend_blood_donation_system.entity.BloodRequest.RequestType;
import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.entity.ComponentType;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.BloodRequestRepository;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import com.example.backend_blood_donation_system.repository.ComponentTypeRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

@Service
public class BloodRequestService {
        @Autowired
        private BloodRequestRepository bloodRequestRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BloodTypeRepository bloodTypeRepository;

        @Autowired
        private ComponentTypeRepository componentTypeRepository;

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        public BloodRequest createRequest(BloodRequestDTO dto, Integer userId) {
                User requester = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                BloodType bloodType = bloodTypeRepository.findById(dto.getBloodTypeId())
                                .orElseThrow(() -> new RuntimeException("Blood type not found"));

                ComponentType component = componentTypeRepository.findById(dto.getComponentTypeId())
                                .orElseThrow(() -> new RuntimeException("Component type not found"));

                BloodRequest request = BloodRequest.builder()
                                .requester(requester)
                                .bloodType(bloodType)
                                .componentType(component)
                                .quantity(dto.getQuantity())
                                .status(RequestStatus.PENDING)
                                .type(RequestType.valueOf(dto.getType()))
                                .requestTime(LocalDateTime.now())
                                .build();

                BloodRequest savedRequest = bloodRequestRepository.save(request);
                messagingTemplate.convertAndSend("/topic/requests", savedRequest);
                return request;
        }

        public List<BloodRequest> getAllRequests() {
                return bloodRequestRepository.findAll();
        }

        public List<BloodRequest> getRequestsByRequester(Integer userId) {
                return bloodRequestRepository.findByRequesterUserId(userId);
        }

        public BloodRequest assignStaff(Integer requestId, RequestStatus status, Integer staffId) {
                BloodRequest request = bloodRequestRepository.findById(requestId)
                                .orElseThrow(() -> new RuntimeException("Request not found"));

                User staff = userRepository.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found"));

                request.setStatus(status);
                request.setAssignedStaff(staff);

                BloodRequest updated = bloodRequestRepository.save(request);

                messagingTemplate.convertAndSendToUser(
                                staff.getUsername(),
                                "/queue/staff-tasks",
                                updated);

                User requester = request.getRequester();
                messagingTemplate.convertAndSendToUser(
                                requester.getUsername(),
                                "/queue/request-status",
                                updated);

                return updated;
        }

        public List<BloodRequest> getRequestsByStaff(Integer staffId) {
                return bloodRequestRepository.findByAssignedStaffUserId(staffId);
        }

        public BloodRequest updateStatus(Integer requestId, RequestStatus status) {
                BloodRequest request = bloodRequestRepository.findById(requestId)
                                .orElseThrow(() -> new RuntimeException("Request not found"));

                request.setStatus(status);
                BloodRequest updatedRequest = bloodRequestRepository.save(request);

                User requester = request.getRequester();
                System.out.println("requester.username = " + requester.getUsername());
                messagingTemplate.convertAndSendToUser(
                                requester.getUsername(),
                                "/queue/request-status",
                                updatedRequest);

                if (status == RequestStatus.COMPLETED || status == RequestStatus.IN_PROGRESS) {
                        messagingTemplate.convertAndSend(
                                        "/topic/requests",
                                        updatedRequest);
                }

                return updatedRequest;
        }

}
