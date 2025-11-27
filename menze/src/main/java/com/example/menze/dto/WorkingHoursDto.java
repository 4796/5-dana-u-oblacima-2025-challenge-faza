package com.example.menze.dto;

import java.time.LocalTime;

import com.example.menze.model.Meal;
import com.example.menze.validation.ValidWorkingHours;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidWorkingHours
public class WorkingHoursDto {

	@NotNull(message = "Meal type is required")
    private Meal meal;
    
    @NotNull(message = "Start time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime from;
    
    @NotNull(message = "End time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime to;
}
