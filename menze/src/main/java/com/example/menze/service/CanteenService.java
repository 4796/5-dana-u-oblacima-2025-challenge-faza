package com.example.menze.service;





import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.menze.dto.CanteenConverter;
import com.example.menze.dto.CanteenDTO;
import com.example.menze.dto.CanteenSlot;
import com.example.menze.dto.CanteenSlotsList;
import com.example.menze.dto.UpdateCanteenRequest;
import com.example.menze.model.Canteen;
import com.example.menze.model.Meal;
import com.example.menze.model.Status;
import com.example.menze.model.Student;
import com.example.menze.model.WorkingHours;
import com.example.menze.repo.CanteenRepository;
import com.example.menze.repo.ReservationRepository;
import com.example.menze.repo.StudentRepository;


import jakarta.validation.Valid;

@Service
public class CanteenService {
	
	@Autowired
	private CanteenRepository canteenRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CanteenConverter converter;
	
	public CanteenDTO create(String studentId, @Valid CanteenDTO request) {
		try {
			Long id = Long.valueOf(studentId);
			Student s = studentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
			
			if (s.getIsAdmin()==null || !s.getIsAdmin()) {
				throw new AccessDeniedException("User is not an admin");
			}
			
			Canteen c = converter.toEntity(request);
			Canteen c1 =canteenRepository.save(c);
			return converter.toDto(c1);
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid student ID format");
		} catch (AccessDeniedException e) {
			throw e;
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating canteen");
		}
	}
	
	public List<Canteen> findAll() {
		try {
			return canteenRepository.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving canteens");
		}
	}
	
	public CanteenDTO findById(String id1) {
		try {
			Long id = Long.valueOf(id1);
			Canteen canteen = canteenRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canteen not found"));
			return converter.toDto(canteen);
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid canteen ID format");
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving canteen");
		}
	}

	

	public CanteenDTO update(String id, String studentId, UpdateCanteenRequest request) {
		try {
			Long idS = Long.valueOf(studentId);
			Student s = studentRepository.findById(idS)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

			if (!s.getIsAdmin()) {
				throw new AccessDeniedException("User is not an admin");
			}
			
			Long idC = Long.valueOf(id);
			Canteen c = canteenRepository.findById(idC)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canteen not found"));
			
			converter.updateEntity(c, request);
			
			Canteen updated = canteenRepository.save(c);
			return converter.toDto(updated);
			
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format");
		} catch (AccessDeniedException e) {
			throw e;
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating canteen");
		}
}

	public void delete(String id, String studentId) {
		try {
			Long idS = Long.valueOf(studentId);
			Student s = studentRepository.findById(idS)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

			if (!s.getIsAdmin()) {
				throw new AccessDeniedException("User is not an admin");
			}
			
			Long idC = Long.valueOf(id);
			Canteen c = canteenRepository.findById(idC)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canteen not found"));
			
			canteenRepository.deleteById(idC);
			
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format");
		} catch (AccessDeniedException e) {
			throw e;
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating canteen");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<CanteenSlotsList> statusForAll(LocalDate startDate, LocalDate endDate, 
            LocalTime startTime, LocalTime endTime, 
            int duration) {
	try {

		validateStatusRequest(startDate, endDate, startTime, endTime, duration);
		
		List<Canteen> canteens = canteenRepository.findAll();
		
		List<CanteenSlotsList> result = new LinkedList<>();
		
		for (Canteen canteen : canteens) {
			CanteenSlotsList slotsList = calculateCanteenSlots(
			canteen, startDate, endDate, startTime, endTime, duration
		);
		
		if (!slotsList.getSlots().isEmpty()) {
			result.add(slotsList);
		}
		}
		
		return result;
	} catch (ResponseStatusException e) {
			throw e;
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
				"Error retrieving canteens status");
	}
	}
	
	public CanteenSlotsList statusForCanteen(String id, LocalDate startDate, LocalDate endDate,
	          LocalTime startTime, LocalTime endTime, 
	          int duration) {
	try {

		validateStatusRequest(startDate, endDate, startTime, endTime, duration);
		
		Long canteenId = Long.valueOf(id);
		Canteen canteen = canteenRepository.findById(canteenId)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
		"Canteen not found"));
		
		return calculateCanteenSlots(canteen, startDate, endDate, startTime, endTime, duration);
	
	} catch (NumberFormatException e) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid canteen ID format");
	} catch (ResponseStatusException e) {
		throw e;
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
				"Error retrieving canteen status");
	}
	}
	
	private void validateStatusRequest(LocalDate startDate, LocalDate endDate,
	    LocalTime startTime, LocalTime endTime,
	    int duration) {
	if (startDate.isAfter(endDate)) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Start date must be before or equal to end date");
	}
	
	if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Start time must be before end time");
	}
	
	if (duration != 30 && duration != 60) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Duration must be 30 or 60 minutes");
	}
	
	if ((startTime.getMinute() != 0 && startTime.getMinute() != 30) ||
	(endTime.getMinute() != 0 && endTime.getMinute() != 30)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Times must be on the hour or half hour (e.g., 12:00 or 12:30)");
	}
	}
	
	private CanteenSlotsList calculateCanteenSlots(Canteen canteen, LocalDate startDate, 
	                LocalDate endDate, LocalTime startTime, 
	                LocalTime endTime, int duration) {
	CanteenSlotsList slotsList = new CanteenSlotsList();
	slotsList.setCanteenId(canteen.getId());
	slotsList.setSlots(new LinkedList<>());
	
	Map<Meal, WorkingHours> workingHours = canteen.getWorkingHours();
	if (workingHours == null || workingHours.isEmpty()) {
	return slotsList;
	}

	LocalDate currentDate = startDate;
	while (!currentDate.isAfter(endDate)) {
	
		for (Map.Entry<Meal, WorkingHours> entry : workingHours.entrySet()) {
			Meal meal = entry.getKey();
			WorkingHours hours = entry.getValue();
			
			if (hours == null || hours.getFrom() == null || hours.getTo() == null) {
			continue;
			}
			
			List<CanteenSlot> mealSlots = generateSlotsForMeal(
			canteen, currentDate, meal, hours, startTime, endTime, duration
			);
			
			slotsList.getSlots().addAll(mealSlots);
		}
		
		currentDate = currentDate.plusDays(1);
	}
	
	return slotsList;
	}
	
	private List<CanteenSlot> generateSlotsForMeal(Canteen canteen, LocalDate date, 
	                Meal meal, WorkingHours hours,
	                LocalTime requestedStartTime, 
	                LocalTime requestedEndTime, 
	                int duration) {
	List<CanteenSlot> slots = new LinkedList<>();
	
	LocalTime effectiveStart = requestedStartTime.isBefore(hours.getFrom()) 
	? hours.getFrom() 
	: requestedStartTime;
	
	LocalTime effectiveEnd = requestedEndTime.isAfter(hours.getTo()) 
	? hours.getTo() 
	: requestedEndTime;
	
	if (effectiveStart.isAfter(effectiveEnd) || effectiveStart.equals(effectiveEnd)) {
	return slots;
	}
	
	LocalTime slotStart = effectiveStart;
	
	while (slotStart.plusMinutes(duration).compareTo(effectiveEnd) <= 0) {
		LocalTime slotEnd = slotStart.plusMinutes(duration);
		
		long activeReservations = reservationRepository.countActiveReservationsInTimeSlot(
		canteen.getId(),
		Status.Active,
		date,
		slotStart,
		slotEnd
		);
		
		long remainingCapacity = canteen.getCapacity() - activeReservations;
		
		CanteenSlot slot = new CanteenSlot();
		slot.setDate(date);
		slot.setMeal(meal);
		slot.setStartTime(slotStart);
		slot.setRemainingCapacity(remainingCapacity);
		
		slots.add(slot);
		
		slotStart = slotStart.plusMinutes(duration);
	}
	
	return slots;
	}
	
}
