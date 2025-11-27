package com.example.menze.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.menze.model.Meal;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CanteenSlot {

	LocalDate date;
	Meal meal;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime startTime;
	Long remainingCapacity;
}
