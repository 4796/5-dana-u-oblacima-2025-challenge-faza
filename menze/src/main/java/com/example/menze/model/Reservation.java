package com.example.menze.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Status status;
	@ManyToOne(optional = false)
	Student student;
	@ManyToOne(optional = false)
	Canteen canteen;
	LocalDate date;
	//LocalTime time;
	private LocalTime startTime;
    private LocalTime endTime;
	Long duration;//30 ili 60
	
	public void setTime(LocalTime time, Long duration){
		startTime=time;
		this.duration=duration;
		endTime = time.plusMinutes(duration);
	}
}
