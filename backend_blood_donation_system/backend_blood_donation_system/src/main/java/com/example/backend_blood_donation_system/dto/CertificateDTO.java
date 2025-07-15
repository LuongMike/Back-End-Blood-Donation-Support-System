package com.example.backend_blood_donation_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private Long id;
    private String certificateCode;
    private LocalDate issueDate;
    // Chỉ lấy những thông tin cần thiết, không lấy toàn bộ object
}