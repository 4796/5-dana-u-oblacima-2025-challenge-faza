package com.example.menze.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.menze.dto.ReservationConverter;
import com.example.menze.dto.ReservationDto;
import com.example.menze.model.Canteen;
import com.example.menze.model.Meal;
import com.example.menze.model.Reservation;
import com.example.menze.model.Status;
import com.example.menze.model.Student;
import com.example.menze.model.WorkingHours;
import com.example.menze.repo.CanteenRepository;
import com.example.menze.repo.ReservationRepository;
import com.example.menze.repo.StudentRepository;

import jakarta.validation.Valid;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository repository;
	@Autowired
    private StudentRepository studentRepository;
	@Autowired
    private CanteenRepository canteenRepository;
	@Autowired
    private ReservationConverter converter;

	 @Transactional
	    public ReservationDto create(@Valid ReservationDto dto) {
	        if (dto.getDate().isBefore(LocalDate.now())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation must be in the past");
	        }
	        
	        LocalTime time = dto.getTime();
	        if (time.getMinute() != 0 && time.getMinute() != 30) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation time not valid");
	        }
	        
	        Student student = studentRepository.findById(dto.getStudentId())
	        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
	        
	        Canteen canteen = canteenRepository.findById(dto.getCanteenId())
	        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canteen not found"));
	        
	        LocalTime endTime = time.plusMinutes(dto.getDuration());
	        
	        //da li je obrok u toku u toj menzi
	        if (!isReservationWithinWorkingHours(canteen, time, endTime)) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "canteen not working.");
	        }
	        
	        //da li student već ima rezervaciju u tom vremenskom intervalu
	        boolean hasOverlap = repository.hasOverlappingReservation(
	        		dto.getStudentId(),
	                Status.Active,
	                dto.getDate(), 
	                time, 
	                endTime
	        );
	        
	        if (hasOverlap) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already has an reservation");
	        }
	        
	        //da li menza ima kapacitet
	        long activeReservations = repository.countActiveReservationsInTimeSlot(
	        		dto.getCanteenId(),
	                Status.Active,
	                dto.getDate(),
	                time,
	                endTime
	        );
	        
	        if (activeReservations >= canteen.getCapacity()) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Canteen does not has capacity.");
	        }
	        
	        //kreiranje rezervacije
	        Reservation reservation = new Reservation();
	        reservation.setStatus(Status.Active);
	        reservation.setStudent(student);
	        reservation.setCanteen(canteen);
	        reservation.setDate(dto.getDate());
	        reservation.setTime(time, dto.getDuration());
	        
	        Reservation saved = repository.save(reservation);
	        
	        return converter.toDto(saved);
	    }
	    
	    @Transactional
	    public ReservationDto cancel(Long studentId, Long reservationId) {
	        Reservation reservation = repository.findByIdAndStudentId(reservationId, studentId)
	        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not valid."));
	        
	        //  već otkazana
	        if (reservation.getStatus() == Status.Cancelled) {
	        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation already cancelled.");
	        }
	        
	        // otkazivanje 
	        reservation.setStatus(Status.Cancelled);
	        Reservation updated = repository.save(reservation);
	        
	        return converter.toDto(updated);
	    }
	    
	    
	    private boolean isReservationWithinWorkingHours(Canteen canteen, LocalTime startTime, LocalTime endTime) {
	        Map<Meal, WorkingHours> workingHours = canteen.getWorkingHours();
	        
	        if (workingHours == null || workingHours.isEmpty()) {
	            return false;
	        }

	        for (WorkingHours hours : workingHours.values()) {
	            if (hours != null && hours.getFrom() != null && hours.getTo() != null) {
	                if (!startTime.isBefore(hours.getFrom()) && !endTime.isAfter(hours.getTo())) {
	                    return true;
	                }
	            }
	        }
	        
	        return false;
	    }
}

