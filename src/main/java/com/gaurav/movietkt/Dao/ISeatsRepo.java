package com.gaurav.movietkt.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaurav.movietkt.model.Seats;

public interface ISeatsRepo extends JpaRepository<Seats, Integer> {
	  @Query("SELECT s FROM Seats s WHERE s.auditoriumFk.auditoriumId = ?1")
	    List<Seats> findByAuditoriumId(Integer auditoriumId);

	Optional<Seats> findBySeatNumber(Integer seatNumber);
	

    @Query("SELECT s FROM Seats s WHERE s.seatNumber = :seatNumber AND s.auditoriumFk.auditoriumId = :auditoriumId")
    Optional<Seats> findBySeatNumberAndAuditoriumFk(@Param("seatNumber") Integer seatNumber, @Param("auditoriumId") Integer auditoriumId);

}
