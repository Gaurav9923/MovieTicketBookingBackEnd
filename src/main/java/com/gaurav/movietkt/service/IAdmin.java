package com.gaurav.movietkt.service;

import java.util.List;

import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Movies;
import com.gaurav.movietkt.model.Screening;

public interface IAdmin {
	public String addMovie(Movies movie);
	public List<Movies> getAllMovie();

	public String addAuditorium(Auditorium auditorium);
	public Auditorium getAuditorium(Integer aditorium_id);
	public List<Auditorium> getAllAuditorium();

	public String addScreeining(Screening screening);
	public Screening getScreeiningWithScreeningId(Integer screeningId);
	public List<Screening> getAllScreening();
	public  Movies getMovieWhithId(Integer movie_id);
}
