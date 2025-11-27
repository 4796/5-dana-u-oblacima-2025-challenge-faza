package com.example.menze.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class WorkingHours {
 
	@Column(name = "start_time")
	private LocalTime from;

	@Column(name = "end_time")
	private LocalTime to;
}
