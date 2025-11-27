package com.example.menze.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = WorkingHoursValidator.class)  
@Documented
public @interface ValidWorkingHours {
    String message() default "End time must be after start time";
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
}
