package com.gaurav.movietkt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaurav.movietkt.model.Reservation;
import com.gaurav.movietkt.model.ReservedSeats;
import com.gaurav.movietkt.model.User;

public interface IReservedSeatsRepo extends JpaRepository<ReservedSeats, Integer> {
    public List<ReservedSeats> findByUserFk(User userFk);
    
    List<ReservedSeats> findByReservationFk(ReservedSeats reservationFk);
    @Query("SELECT r FROM ReservedSeats r WHERE r.reservationFk.reservationId = :reservationId")
    List<ReservedSeats> findByReservationFkId(@Param("reservationId") Integer reservationId);
   
    
    @Query("SELECT r FROM ReservedSeats r WHERE r.screeningFk.auditorumFk.auditoriumId = :auditoriumId")
	 List<ReservedSeats> findByAuditoriumId(@Param("auditoriumId") Integer auditoriumId);
    
    @Query("SELECT rs FROM ReservedSeats rs WHERE rs.reservedSeatId = :reservedSeatId")
    ReservedSeats findByReservedSeatId(@Param("reservedSeatId") Integer reservedSeatId);
}
