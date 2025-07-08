package com.example.backend_blood_donation_system.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.AppointmentDTO;
import com.example.backend_blood_donation_system.dto.AppointmentRequestDTO;
import com.example.backend_blood_donation_system.entity.Appointment;
import com.example.backend_blood_donation_system.entity.DonationCenter;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.AppointmentRepository;
import com.example.backend_blood_donation_system.repository.DonationCenterRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;

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

        DonationCenter center = donationCenterRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Donation Center not found"));

        appointment.setUser(user);
        appointment.setCenter(center);
        appointment.setScheduledDate(Date.valueOf(request.getScheduledDate()));
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    // Phương thức để lấy tất cả các cuộc hẹn
    public List<AppointmentDTO> getAllAppointmentDTOs() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Phương thức để lấy các cuộc hẹn theo ngày
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByScheduledDate(Date.valueOf(date)); // chuyển từ LocalDate -> java.sql.Date
    }

    // Phương thức cập nhật kết quả sàng lọc (Screening)
    public boolean updateScreeningResult(Integer appointmentId, boolean passed, String remarks) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();

            if (passed) {
                appointment.setScreeningResult("Passed");
                appointment.setStatus("APPROVED");
            } else {
                appointment.setScreeningResult("Failed");
                appointment.setStatus("REJECTED");
            }
            appointment.setRemarks(remarks);
            appointmentRepository.save(appointment);
            return true;
        } else {
            return false;
        }
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setUserId(appointment.getUser().getUserId());
        dto.setCenterId(appointment.getCenter().getCenterId());
        dto.setScheduledDate(appointment.getScheduledDate().toLocalDate());
        dto.setStatus(appointment.getStatus());
        dto.setScreeningResult(appointment.getScreeningResult());
        dto.setRemarks(appointment.getRemarks());
        return dto;
    }

}
