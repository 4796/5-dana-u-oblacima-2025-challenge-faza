package com.example.menze.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.menze.dto.ReservationDto;
import com.example.menze.model.Reservation;
import com.example.menze.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;


@PostMapping
public ResponseEntity<ReservationDto> create(@Valid @RequestBody ReservationDto dto){
	ReservationDto r = reservationService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(r);
}


@DeleteMapping("/{id}")
public ResponseEntity<ReservationDto> cancel(@RequestHeader("studentId") Long studentId, @PathVariable Long id){
	ReservationDto r = reservationService.cancel(studentId, id);
	return ResponseEntity.ok(r);
}


}
