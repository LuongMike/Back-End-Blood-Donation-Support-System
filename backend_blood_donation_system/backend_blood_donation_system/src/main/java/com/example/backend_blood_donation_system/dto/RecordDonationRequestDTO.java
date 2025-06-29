package com.example.backend_blood_donation_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecordDonationRequestDTO {

    @NotNull(message = "Appointment ID is required")
    private Integer appointmentId;

    @NotNull(message = "Blood Type ID is required")
    private Long bloodTypeId;

    @NotNull(message = "Component Type ID is required")
    private Long componentTypeId;

    @NotNull(message = "Units are required")
    @Positive(message = "Units must be a positive number")
    private Integer units;

    // Getters and Setters
    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(Long bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }
}