package com.example.backend_blood_donation_system.dto;

import com.example.backend_blood_donation_system.entity.BloodType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private Double weight;
//    @EnumValue(name = "blood_group", enumClass = BloodType.class)
//@ManyToOne
//@JoinColumn(name = "blood_type_id")
    private String blood_type;  // ví dụ: "A+", "O-", ...
    private String healthStatus;
}
