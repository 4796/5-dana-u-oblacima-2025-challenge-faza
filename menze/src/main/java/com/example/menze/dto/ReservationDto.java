package com.example.menze.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.menze.model.Status;
import com.example.menze.validation.ValidDuration;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationDto {

	Long id;
	Status status;
	@NotNull
	@Positive
	Long studentId;
	@NotNull
	@Positive
	Long canteenId;
	@NotNull
	LocalDate date;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime time;
	@NotNull
	@ValidDuration
	Long duration;
}
