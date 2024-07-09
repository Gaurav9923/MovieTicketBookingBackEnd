package com.gaurav.movietkt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Seats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer seatId;
    
	
    
	private Integer seatNumber;
	
   

	
	

	
   @ManyToOne(targetEntity =Auditorium.class,cascade = CascadeType.ALL)
   @JoinColumn(name="auditoriumId")
   private Auditorium auditoriumFk;
    


}
