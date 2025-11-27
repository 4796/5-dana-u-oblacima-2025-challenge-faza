package com.example.menze.dto;

import org.springframework.stereotype.Component;

import com.example.menze.model.Canteen;
import com.example.menze.model.Reservation;
import com.example.menze.model.Student;

@Component
public class ReservationConverter {

    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStatus(reservation.getStatus());
        dto.setStudentId(reservation.getStudent() != null ? reservation.getStudent().getId() : null);
        dto.setCanteenId(reservation.getCanteen() != null ? reservation.getCanteen().getId() : null);
        dto.setDate(reservation.getDate());
        dto.setTime(reservation.getStartTime());
        dto.setDuration(reservation.getDuration());

        return dto;
    }

    public Reservation toEntity(ReservationDto dto) {
        if (dto == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setStatus(dto.getStatus());
        if (dto.getStudentId() != null) {
            Student student = new Student();
            student.setId(dto.getStudentId());
            reservation.setStudent(student);
        }
        if (dto.getCanteenId() != null) {
            Canteen canteen = new Canteen();
            canteen.setId(dto.getCanteenId());
            reservation.setCanteen(canteen);
        }
        reservation.setDate(dto.getDate());
        reservation.setTime(dto.getTime(), dto.getDuration());
        reservation.setDuration(dto.getDuration());

        return reservation;
    }
}
