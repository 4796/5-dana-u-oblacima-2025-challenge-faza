package com.example.menze.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CanteenDTO {
	private Long id;
	@NotBlank
    private String name;
	@NotBlank
    private String location;
    @Positive
    private Integer capacity;
    @NotNull(message = "Working hours are required")
    @Size(min = 1, message = "At least one working hour entry is required")
    @Valid//nested
    private List<WorkingHoursDto> workingHours;


}
