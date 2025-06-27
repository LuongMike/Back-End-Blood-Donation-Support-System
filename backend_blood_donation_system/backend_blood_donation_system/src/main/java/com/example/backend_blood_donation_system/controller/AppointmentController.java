package com.example.backend_blood_donation_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.AppointmentRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.service.AppointmentService;

@RestController
@RequestMapping("/api/user/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/register")
    public Appointment registerAppointment(@RequestBody AppointmentRequestDTO request) {
        return appointmentService.createAppointment(request);
    }

   
}
