package com.example.backend_blood_donation_system.repository;
import com.example.backend_blood_donation_system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<   Appointment, Integer> {
}