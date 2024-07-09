package com.gaurav.movietkt.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Movies;
import com.gaurav.movietkt.model.Screening;
import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.service.IAdmin;
import com.gaurav.movietkt.service.IUser;
import com.gaurav.movietkt.service.IUserOperationImpl;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins  = "*")
@RestController
@RequestMapping("api/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	private IAdmin adminService;
	
	@Autowired
	private IUser userService;
	@Autowired
	private IUserOperationImpl userOperationImpl;
	
	
	 @GetMapping("/fetch/{uid}")
	    public ResponseEntity<?> fetchUser(@PathVariable("uid") Integer uid){
		 	log.info("Inside AdminController.fetchUser()");
		 
	    	User user=userService.fetchUser(uid);
	    	if(user!=null)
	    	return new ResponseEntity<User>(user,HttpStatus.OK);
	    	else {
				// TODO: handle exception
	    		return new ResponseEntity<String>("user is empty",HttpStatus.NOT_FOUND);
			}
	    }
	    
	    @GetMapping("/fetchAll")
	    public ResponseEntity<?> getUserList(){
	    	log.info("Inside AdminController.getUserList()");
	    	List<User> list=userService.fetchAllUser();
	    	return new ResponseEntity<List<User>>(list,HttpStatus.OK);
	    }
	
	    @GetMapping("/checkAvailableBooking/{audId}")
	    public ResponseEntity<?> checkAvailibility(@PathVariable("audId") Integer audID ){
	    	log.info("Inside AdminController.checkAvailibility()");
	    	Map<String,Integer> temp=userOperationImpl.getCurrentAvalibility(audID);
	    	
	    	temp.put("audId", audID);
//	    	System.out.println("AdminController.checkAvailibility():temp-->"+temp);
	    	return new ResponseEntity<Map<String,Integer>>(temp,HttpStatus.OK);
	    }
	    
	
	
	@PostMapping("/addMovie")
	public ResponseEntity<?> addAdmin(@RequestBody Movies movie){
		log.info("Inside AdminController.addAdmin()");
//		System.out.println(movie);
		    String temp=movie.getMovieName();
			String m=adminService.addMovie(movie);
			if(m.equals(temp)) {
//				return new ResponseEntity<String>(m+" added succesfully",HttpStatus.OK);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				
				return new ResponseEntity<String>(m+" not added succesfully",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	
	
	@GetMapping("/moviesList")
	public ResponseEntity<?> getMovieList(){
		List<Movies> lis=adminService.getAllMovie();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Movie Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Movies>>(lis,HttpStatus.OK);
			
			
		}
	}
	
	@GetMapping("/getMovie/{id}")	
	public ResponseEntity<?> getMovieWithId(@PathVariable("id") Integer id){
		
		log.info("Inside AdminController.getMovieWithId()");
		Movies lis=adminService.getMovieWhithId(id);
		if(lis==null) {
			return new ResponseEntity<String>("No Movie Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Movies>(lis,HttpStatus.OK);
			
			
		}
	}
	
	
	@PostMapping("/addAuditorium")
	public ResponseEntity<?> addAuditorium(@RequestBody Auditorium aditorium){
		
		log.info("Inside AdminController.addAuditorium()");
		String auditName=adminService.addAuditorium(aditorium);
		if(aditorium.getAuditoriumName().equals(auditName))
//		return new ResponseEntity<String>(auditName+" :: svaed successfully",HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(aditorium.getAuditoriumName()+" ::not  svaed successfully",HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	
	@GetMapping("/auditoriumList")
	public ResponseEntity<?> getAuditoriumList(){
		log.info("Inside AdminController.getAuditoriumList()");
		List<Auditorium> lis=adminService.getAllAuditorium();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Auditorium Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Auditorium>>(lis,HttpStatus.OK);
			
			
		}
	}
	
	@GetMapping("/getAuditorium/{auditorium_id}")
	public ResponseEntity<?> getAuditorium(@PathVariable("auditorium_id") Integer auditorium_id){
		log.info("Inside AdminController.getAuditorium()");
		
		Auditorium audit=adminService.getAuditorium(auditorium_id);
		if(audit!=null) {
			
			return new ResponseEntity<Auditorium>(audit,HttpStatus.OK);
		}
		else {
			
			return new ResponseEntity<String>(auditorium_id+" ::Not Found",HttpStatus.NOT_FOUND);
		}
		
			
		}
	
	
	
	
	@GetMapping("/screeningList")
	public ResponseEntity<?> getScreeningList(){
		log.info("Inside AdminController.getScreeningList()");
		
		List<Screening> lis=adminService.getAllScreening();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Scrreining Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Screening>>(lis,HttpStatus.OK);
			
			
		}
	}
	@GetMapping("/screening/{auditorium_id}")
	public ResponseEntity<?> getScreeningByAuditoriumID(@PathVariable("auditorium_id") Integer auditorium_id){
		log.info("Inside AdminController.getScreeningByAuditoriumID()");
		
		List<Screening> lis=adminService.getAllScreening();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Scrreining Listed",HttpStatus.NOT_FOUND);
		}else {
			
			Optional<Screening> op=lis.stream().filter(f->f.getAuditorumFk().getAuditoriumId()==auditorium_id)
						.findAny();
			return new ResponseEntity<Screening>(op.get(),HttpStatus.OK);
			
			
		}
	}
	
	@PostMapping("/setScreening")
	public ResponseEntity<?> setScreening(@RequestParam("movieId") Integer mid,@RequestParam("auditId")Integer aid,@RequestParam("screeningId")Integer sid){
		log.info("Inside AdminController.setScreening()");
//		System.out.println(mid+"-"+aid+"-"+sid);
		Movies movie=adminService.getMovieWhithId(mid);
		Auditorium audit=adminService.getAuditorium(aid);
		Screening screen=adminService.getScreeiningWithScreeningId(sid);
//		Screening screen=new Screening();
		screen.setAuditorumFk(audit);
		screen.setMovieFk(movie);
		
       String output= adminService.addScreeining(screen);
       if(output.equals("200")) {
    	   
    	   return new ResponseEntity<>(HttpStatus.OK);
       }
       return new ResponseEntity<String>(output,HttpStatus.OK);
		
	}

	
	
	
}
	
	


