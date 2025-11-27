package com.example.menze.control;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.menze.dto.CanteenSlot;
import com.example.menze.dto.CanteenSlotsList;
import com.example.menze.dto.UpdateCanteenRequest;
import com.example.menze.dto.CanteenConverter;
import com.example.menze.dto.CanteenDTO;
import com.example.menze.model.Canteen;
import com.example.menze.service.CanteenService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/canteens")
@Validated
public class CanteenController {

	@Autowired
    private CanteenService canteenService;
	@Autowired
	private CanteenConverter canteenConverter;


    @PostMapping
    public ResponseEntity<CanteenDTO> createCanteen(
            @RequestHeader("studentId") String studentId,
            @Valid @RequestBody CanteenDTO request
    ) throws Exception {
        CanteenDTO created = canteenService.create(studentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CanteenDTO>> getAllCanteens() {
        List<Canteen> list = canteenService.findAll();
        List<CanteenDTO> response = list.stream()
        	    .map(canteenConverter::toDto)
        	    .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanteenDTO> getCanteenById(@PathVariable("id") String id) {
        CanteenDTO dto = canteenService.findById(id);
                
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanteenDTO> updateCanteen(
            @PathVariable("id") String id,
            @RequestHeader("studentId") String studentId,
            @Valid @RequestBody UpdateCanteenRequest request
    ) {
        CanteenDTO updated = canteenService.update(id, studentId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCanteen(
            @PathVariable("id") String id,
            @RequestHeader("studentId") String studentId
    ) {
        canteenService.delete(id, studentId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    
    @GetMapping("/status")
    public ResponseEntity<List<CanteenSlotsList>> getCanteensStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam @Min(1) int duration
    ) {
    	List<CanteenSlotsList> resp = canteenService.statusForAll(startDate, endDate, startTime, endTime, duration);
        return ResponseEntity.ok(resp);
    }
    
    

    @GetMapping("/{id}/status")//id of canteen
    public ResponseEntity<CanteenSlotsList> getCanteenStatusById(
            @PathVariable("id") String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam @Min(1) int duration
    ) {
    	CanteenSlotsList cs = canteenService.statusForCanteen(id, startDate, endDate, startTime, endTime, duration);
        return ResponseEntity.ok(cs);
    }

    

   

    

    
    //CanteenSlotsList resp = dtos.stream().map(CanteenStatusResponse::fromDto).toList();
    

    
}
