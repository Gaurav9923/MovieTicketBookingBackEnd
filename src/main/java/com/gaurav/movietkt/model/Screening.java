package com.gaurav.movietkt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Screening {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer screeningId; 
	
  
	



	//theaterScreenId  from Screen_shows_mapper added to Screen 
	@OneToOne(targetEntity =Movies.class,cascade = CascadeType.ALL)
	@JoinColumn(name="movieId")
	private Movies movieFk;
	
	@OneToOne(targetEntity =Auditorium.class,cascade = CascadeType.PERSIST)
	@JoinColumn(name="auditoriumId")
	private Auditorium auditorumFk;
	
//	@ManyToOne(targetEntity = Reservation.class, cascade = CascadeType.ALL)
//	@JoinColumn(name = "reservationIdFk", referencedColumnName = "reservationId")
//	private Reservation reservationFk;
	

	
}
