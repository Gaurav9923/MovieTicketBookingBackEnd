package com.gaurav.movietkt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer theaterId; 
	
	private String theaterName; 
	
	@OneToOne(targetEntity =Address.class)
	@JoinColumn(name="addressId",referencedColumnName ="addressId" )
	private Address address; 

////	private List<Movie> moviesListInTheaters; 
//	private Float theaterRating; 


	private Integer theaterMaxScreen; 



	
	
	


}
