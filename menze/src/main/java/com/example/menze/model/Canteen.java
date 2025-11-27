package com.example.menze.model;

import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import lombok.Data;
import jakarta.persistence.JoinColumn;

@Entity
@Data
public class Canteen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
	String name;
	String location;
	int capacity;
	
	
	 @ElementCollection
	 @CollectionTable(name = "canteen_working_hours", joinColumns = @JoinColumn(name = "canteen_id"))
	 @MapKeyEnumerated(EnumType.STRING)
	 @MapKeyColumn(name = "meal_type")
	 private Map<Meal, WorkingHours> workingHours = new EnumMap<>(Meal.class);
	 
	 private void initializeWorkingHours() {
	        workingHours.put(Meal.breakfast, new WorkingHours());
	        workingHours.put(Meal.lunch, new WorkingHours());
	        workingHours.put(Meal.dinner, new WorkingHours());
	    }
}
