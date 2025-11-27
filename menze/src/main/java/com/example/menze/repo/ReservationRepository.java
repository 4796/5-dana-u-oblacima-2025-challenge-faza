package com.example.menze.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.menze.model.Reservation;
import com.example.menze.model.Status;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    Optional<Reservation> findByIdAndStudentId(Long id, Long studentId);
    
    
     // da li student već ima aktivnu rezervaciju u datom vremenskom intervalu

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.student.id = :studentId " +
            "AND r.status = :status " +
            "AND r.date = :date " +
            "AND NOT (r.endTime <= :startTime OR r.startTime >= :endTime)")
     boolean hasOverlappingReservation(@Param("studentId") Long studentId,
                                      @Param("status") Status status,
                                      @Param("date") LocalDate date,
                                      @Param("startTime") LocalTime startTime,
                                      @Param("endTime") LocalTime endTime);
    
    
     // Broj aktivnih rezervacija za menzu u određenom vremenskom intervalu (za proveru kapaciteta)
     
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.canteen.id = :canteenId " +
            "AND r.status = :status " +
            "AND r.date = :date " +
            "AND NOT (r.endTime <= :startTime OR r.startTime >= :endTime)")
     long countActiveReservationsInTimeSlot(@Param("canteenId") Long canteenId,
                                            @Param("status") Status status,
                                            @Param("date") LocalDate date,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime);
    

    List<Reservation> findByCanteenIdAndStatus(Long canteenId, Status status);
}