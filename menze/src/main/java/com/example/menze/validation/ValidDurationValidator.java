package com.example.menze.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ValidDurationValidator implements ConstraintValidator<ValidDuration, Long> {

    private static final Set<Long> ALLOWED_VALUES = Set.of(30L, 60L);

    @Override
    public boolean isValid(Long duration, ConstraintValidatorContext context) {
        if (duration == null) {
            return true;
        }
        return ALLOWED_VALUES.contains(duration);
    }
}
