package com.example.menze.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDurationValidator.class)
@Documented
public @interface ValidDuration {
    String message() default "Duration must be 30 or 60 minutes";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
