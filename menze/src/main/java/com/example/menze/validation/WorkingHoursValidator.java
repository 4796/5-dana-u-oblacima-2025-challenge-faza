package com.example.menze.validation;

import com.example.menze.dto.WorkingHoursDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WorkingHoursValidator implements ConstraintValidator<ValidWorkingHours, WorkingHoursDto> {
    
    @Override
    public boolean isValid(WorkingHoursDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getFrom() == null || dto.getTo() == null) {
            return true; 
        }
        
        return dto.getFrom().isBefore(dto.getTo());
    }
}
