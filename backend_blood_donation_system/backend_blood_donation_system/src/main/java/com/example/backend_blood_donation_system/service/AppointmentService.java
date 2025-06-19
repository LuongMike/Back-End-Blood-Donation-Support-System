package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.AppointmentRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.entity.DonationCenter;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.AppointmentRepository;
import com.example.backend_blood_donation_system.repository.DonationCenterRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationCenterRepository donationCenterRepository;

    public Appointment createAppointment(AppointmentRequestDTO request) {
        Appointment appointment = new Appointment();

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy DonationCenter mặc định có id = 1
        DonationCenter center = donationCenterRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Donation Center not found"));

        appointment.setUser(user);
        appointment.setCenter(center);
        appointment.setScheduledDate(Date.valueOf(request.getScheduledDate()));
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }
}

