package com.example.backend_blood_donation_system.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_blood_donation_system.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    
    // Tìm kiếm các cuộc hẹn theo ngày đã lên lịch
    List<Appointment> findByScheduledDate(Date scheduledDate);

}