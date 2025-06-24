package com.example.backend_blood_donation_system.dto;


public class ScreeningRequestDTO {
    private boolean passed;
    private String remarks;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
