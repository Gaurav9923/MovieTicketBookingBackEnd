package com.gaurav.movietkt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservedSeats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer reservedSeatId;
    
	
    @ManyToOne(targetEntity = User.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name="userId")   
	private User userFk;
	
	

	
   @OneToOne(targetEntity =Seats.class,cascade = CascadeType.ALL)
   @JoinColumn(name="seatId")  
   private Seats seatFk;
   
   @ManyToOne(targetEntity =Screening.class,cascade = CascadeType.PERSIST)
	@JoinColumn(name="screeningId")   
	private Screening screeningFk;
    
   @ManyToOne(targetEntity = Reservation.class, cascade = CascadeType.PERSIST)
	@JoinColumn(unique = false ,name = "reservationIdFk", referencedColumnName = "reservationId")
  	private Reservation reservationFk;


}
