package com.gaurav.movietkt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"reservedSeats"})
public class Reservation {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId; 
	



	@JsonIgnore
	@OneToMany(targetEntity =ReservedSeats.class,cascade = CascadeType.ALL,mappedBy = "reservationFk")
	private List<ReservedSeats> reservedSeatIdFk;


    @ManyToOne(targetEntity =User.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name="userId")
    private User userFk;

    
    public void addReservedSeatsHelper(ReservedSeats resSeat) {
    	reservedSeatIdFk.add(resSeat);
    	resSeat.setReservationFk(this);
    }



}
