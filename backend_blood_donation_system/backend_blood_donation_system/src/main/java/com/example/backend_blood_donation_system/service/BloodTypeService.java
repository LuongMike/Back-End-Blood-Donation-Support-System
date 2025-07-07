package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.BloodTypeDTO;
import com.example.backend_blood_donation_system.entity.BloodType;
import com.example.backend_blood_donation_system.repository.BloodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BloodTypeService {
    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    public List<BloodTypeDTO> getAllBloodTypes() {
        List<BloodType> bloodTypes = bloodTypeRepository.findAll();
        return bloodTypes.stream()
                .map(b -> new BloodTypeDTO(b.getBlood_type_id(), b.getType()))
                .toList();
    }
}
