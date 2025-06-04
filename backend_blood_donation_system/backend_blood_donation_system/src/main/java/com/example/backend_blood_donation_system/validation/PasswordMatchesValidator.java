package com.example.backend_blood_donation_system.validation;

import com.example.backend_blood_donation_system.dto.UserRegistrationDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDTO> {
    @Override
    public boolean isValid(UserRegistrationDTO dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        boolean matched = dto.getPassword().equals(dto.getConfirmPassword());
        if (!matched) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Confirm password must match password")
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();
        }
        return matched;
    }
}