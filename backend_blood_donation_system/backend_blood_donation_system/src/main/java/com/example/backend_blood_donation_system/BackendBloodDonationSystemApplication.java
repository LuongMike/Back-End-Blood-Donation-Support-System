package com.example.backend_blood_donation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // Import thêm

@SpringBootApplication
// THÊM DÒNG NÀY VÀO
// Chỉ định rõ các gói chứa Entity cần quét
@EntityScan(basePackages = {"com.example.backend_blood_donation_system.entity", "com.another.package.with.entities"}) 
public class BackendBloodDonationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendBloodDonationSystemApplication.class, args);
    }
}