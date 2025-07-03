package com.example.backend_blood_donation_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend_blood_donation_system.dto.DonationHistoryDTO;
import com.example.backend_blood_donation_system.dto.RecordDonationRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.entity.BloodInventory;
import com.example.backend_blood_donation_system.entity.BloodInventoryId;
import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.entity.ComponentType;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.repository.AppointmentRepository;
import com.example.backend_blood_donation_system.repository.BloodInventoryRepository;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import com.example.backend_blood_donation_system.repository.ComponentTypeRepository;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    // Lấy tất cả lịch sử và chuyển đổi sang DTO
    public List<DonationHistoryDTO> getAll() {
        return donationHistoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy lịch sử theo User ID và chuyển đổi sang DTO
    public List<DonationHistoryDTO> getByUserId(Long userId) {
        // Lưu ý: Cần có phương thức findByUserUserId trong DonationHistoryRepository
        return donationHistoryRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    /**
     * Ghi lại một ca hiến máu đã hoàn thành.
     */
    @Transactional
    public DonationHistoryDTO recordDonation(RecordDonationRequestDTO requestDTO) {
        // 1. Tìm Appointment dựa vào ID
        Appointment appointment = appointmentRepository.findById(requestDTO.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + requestDTO.getAppointmentId()));

        // 2. Kiểm tra trạng thái của Appointment, phải là 'APPROVED'
        if (!"APPROVED".equalsIgnoreCase(appointment.getStatus())) {
            throw new IllegalStateException("Cannot record donation for an appointment that is not in 'APPROVED' status. Current status: " + appointment.getStatus());
        }

        // 3. Lấy các entity liên quan
        BloodType bloodType = bloodTypeRepository.findById(requestDTO.getBloodTypeId())
                .orElseThrow(() -> new EntityNotFoundException("BloodType not found with id: " + requestDTO.getBloodTypeId()));

        ComponentType componentType = componentTypeRepository.findById(requestDTO.getComponentTypeId())
                .orElseThrow(() -> new EntityNotFoundException("ComponentType not found with id: " + requestDTO.getComponentTypeId()));

        // 4. Tạo đối tượng DonationHistory mới
        DonationHistory donationHistory = new DonationHistory();
        donationHistory.setAppointment(appointment);
        donationHistory.setUser(appointment.getUser());
        donationHistory.setCenter(appointment.getCenter());
        donationHistory.setBloodType(bloodType);
        donationHistory.setComponentType(componentType);
        donationHistory.setUnits(requestDTO.getUnits());
        donationHistory.setDonationDate(LocalDate.now());

        // 5. Lưu bản ghi DonationHistory mới
        DonationHistory savedHistory = donationHistoryRepository.save(donationHistory);

        // 6. Cập nhật trạng thái của Appointment
        appointment.setStatus("COMPLETED");
        appointmentRepository.save(appointment);

        // --- PHẦN MỚI: CẬP NHẬT KHO MÁU (BLOOD INVENTORY) ---
        // Tạo ID phức hợp cho kho máu
        // Đoạn code đã được sửa lại cho đúng
        BloodInventoryId inventoryId = new BloodInventoryId(bloodType.getId(), componentType.getId());

        // Tìm bản ghi trong kho, nếu không có thì tạo mới
        BloodInventory inventoryItem = bloodInventoryRepository.findById(inventoryId)
                .orElse(new BloodInventory());
        
        if (inventoryItem.getId() == null) {
            // Trường hợp TẠO MỚI
            inventoryItem.setId(inventoryId);
            inventoryItem.setUnitsAvailable(requestDTO.getUnits());
        } else {
            // Trường hợp CẬP NHẬT (cộng dồn)
            int currentUnits = inventoryItem.getUnitsAvailable();
            inventoryItem.setUnitsAvailable(currentUnits + requestDTO.getUnits());
        }
        
        // Lưu lại thay đổi vào kho máu
        bloodInventoryRepository.save(inventoryItem);
        // --------------------------------------------------------
        
        // 7. Chuyển đổi entity đã lưu thành DTO để trả về
        return convertToDTO(savedHistory);
    }

    // SỬA: Phương thức helper đã được sửa lại để khớp chính xác với DTO của bạn
    private DonationHistoryDTO convertToDTO(DonationHistory history) {
        DonationHistoryDTO dto = new DonationHistoryDTO();
        dto.setDonationId(history.getDonationId());
        
        // SỬA: Khớp với trường `userFullName` trong DTO
        // Giả sử User entity có phương thức getFullName()
        dto.setUserFullName(history.getUser().getFullName()); 
        
        // Giả sử DonationCenter entity có phương thức getName()
        dto.setCenterName(history.getCenter().getName()); 

        // SỬA: Khớp với trường `bloodType` và getter `getType()` trong BloodType entity
        dto.setBloodType(history.getBloodType().getType());
        
        // Giả sử ComponentType entity có phương thức getName()
        dto.setComponentType(history.getComponentType().getName());

        dto.setDonationDate(history.getDonationDate());
        dto.setUnits(history.getUnits());
        return dto;
    }
}