package com.example.backend_blood_donation_system.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmailRequestDTO {
    private List<Integer> userIds;
}