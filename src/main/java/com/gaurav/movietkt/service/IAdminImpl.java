package com.gaurav.movietkt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.movietkt.Dao.IAuditoriumRepo;
import com.gaurav.movietkt.Dao.IMovieRepo;
import com.gaurav.movietkt.Dao.IScreeningRepo;
import com.gaurav.movietkt.Exception.UserException.NotFoundException;
import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Movies;
import com.gaurav.movietkt.model.Screening;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class IAdminImpl implements IAdmin {
	@Autowired
	private IMovieRepo movieRepo;
	
	@Autowired
	private IAuditoriumRepo aditoriumRepo;
	
	@Autowired
	private IScreeningRepo screeningRepo;
	
////movie crud--->
	
	@Override
	public String addMovie(Movies movie) {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.addMovie()");
		if(movieRepo.save(movie).getMovieName().isBlank())
            throw new NotFoundException(" Movies not added in Table");
		return movieRepo.save(movie).getMovieName();
	}
	
	@Override
	public Movies getMovieWhithId(Integer movie_id) {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.getMovieWhithId()");
		if(movieRepo.findById(movie_id).isEmpty())
            throw new NotFoundException("Movies not exists");
		Optional<Movies> op=movieRepo.findById(movie_id);
		return op.get();
	}



	@Override
	public List<Movies> getAllMovie() {
		log.info("Inside IAdminImpl.getAllMovie()");
		if(movieRepo.findAll().isEmpty())
            throw new NotFoundException("No Movies in Table");
		return movieRepo.findAll();
	}

//Auditorium crud---->
	@Override
	public String addAuditorium(Auditorium auditorium) {
		log.info("Inside IAdminImpl.addAuditorium()");
		if(aditoriumRepo.save(auditorium).getAuditoriumName().isEmpty())
            throw new NotFoundException("Auditorium not added or Already present");
		return aditoriumRepo.save(auditorium).getAuditoriumName();
	}


	@Override
	public Auditorium getAuditorium(Integer aditorium_id) {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.getAuditorium()");
		if(aditoriumRepo.findById(aditorium_id).isEmpty())
            throw new NotFoundException("Auditorium not exists");
		Optional<Auditorium> op=aditoriumRepo.findById(aditorium_id);
		return op.get();
	}
	



	@Override
	public List<Auditorium> getAllAuditorium() {
		log.info("Inside IAdminImpl.getAllAuditorium()");
		// TODO Auto-generated method stub
		if(aditoriumRepo.findAll().isEmpty())
            throw new NotFoundException("No Auditorium Registered");
		return aditoriumRepo.findAll();
	}


	
	
	
//Screen crude---->
	
	@Override
	public String addScreeining(Screening screening) {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.addScreeining()");
		if(screeningRepo.save(screening)==null)
            throw new NotFoundException("Screening not Registered");
		Screening temp =screeningRepo.save(screening);
		if(temp!=null) {
//			changed for angular no string in response send
//			return temp.getScreeningId()+" "+temp.getMovieFk()+" "+temp.getAuditorumFk();
			return "200";
		}
		return "NOT ABALE TO GET AUDITORIUM NAME";
	}
	@Override
	public List<Screening> getAllScreening() {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.getAllScreening()");
		
		if(screeningRepo.findAll().isEmpty())
            throw new NotFoundException("No Screening Registered");
		return screeningRepo.findAll();
	}

	@Override
	public Screening getScreeiningWithScreeningId(Integer screeningId) {
		// TODO Auto-generated method stub
		log.info("Inside IAdminImpl.getScreeiningWithScreeningId()");
		Optional<Screening>op=screeningRepo.findById(screeningId);
		return op.get();
	}


	
	


	


	
}
