package com.gaurav.movietkt.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaurav.movietkt.model.Reservation;
import com.gaurav.movietkt.model.User;

public interface IReservationRepo extends JpaRepository<Reservation, Integer> {

	 @Query("SELECT r FROM Reservation r WHERE r.userFk = :user")
	    List<Reservation> findByUserFk(@Param("user") User user);

	
	
}
