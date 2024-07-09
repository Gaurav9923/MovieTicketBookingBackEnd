package com.gaurav.movietkt.restController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.movietkt.model.Movies;
import com.gaurav.movietkt.model.Role;
import com.gaurav.movietkt.model.Screening;
import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.service.IAdmin;
import com.gaurav.movietkt.service.IUser;
import com.gaurav.movietkt.service.IUserOperation;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins  = "*")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	
    @Autowired   
	private IUser userService;
    @Autowired   
    private IAdmin adminService;
    
    @Autowired
	IUserOperation userOperationService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
	
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
    	log.info("Inside UserController.registerUser()");
    	log.info("registering--->"+user);
    	try {
    	String name=userService.registerUser(user);
    	return new ResponseEntity<String>(name+":: registered succesfully",HttpStatus.OK);
    	}catch (Exception e) {
			// TODO: handle exception
    		return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
   
    @GetMapping("/moviesList")
	public ResponseEntity<?> getMovieList(){
    	
    	log.info("Inside UserController.getMovieList()");
		List<Movies> lis=adminService.getAllMovie();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Movie Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Movies>>(lis,HttpStatus.OK);
			
			
		}
	}
	
    @GetMapping("/moviesSearchList/{searchString}")
    public ResponseEntity<?> getMovieSearchList(@PathVariable("searchString") String searchString){
    	
    	log.info("Inside UserController.getMovieSearchList()");
    	List<Movies> lis=adminService.getAllMovie();
    	if(lis.isEmpty()) {
    		return new ResponseEntity<String>("No Movie found for given search",HttpStatus.NOT_FOUND);
    	}else {
    		
    		List<Movies>searchLis=lis.stream().filter(f->f.getMovieName().toLowerCase().contains(searchString.toLowerCase())).collect(Collectors.toList());
    		System.out.println(searchLis);
    		return new ResponseEntity<List<Movies>>(searchLis,HttpStatus.OK);
    		
    		
    	}
    }
    
	@GetMapping("/getMovie/{id}")
	public ResponseEntity<?> getMovieWithId(@PathVariable("id") Integer id){
		log.info("Inside UserController.getMovieWithId()");
		Movies lis=adminService.getMovieWhithId(id);
		if(lis==null) {
			return new ResponseEntity<String>("No Movie Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Movies>(lis,HttpStatus.OK);
			
			
		}
	}
	
	
	@GetMapping("/screeningList")
	public ResponseEntity<?> getScreeningList(){
		log.info("Inside UserController.getScreeningList()");
		List<Screening> lis=adminService.getAllScreening();
		if(lis.isEmpty()) {
			return new ResponseEntity<String>("No Scrreining Listed",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<List<Screening>>(lis,HttpStatus.OK);
			
			
		}
	}
	
	 @GetMapping("/fetch/{uid}")	
	  public ResponseEntity<?> fetchPersonalInfo(@PathVariable("uid") Integer uid){
		 log.info("Inside UserController.fetchPersonalInfo()");
		 
		 	User user=userService.fetchUser(uid);
//	    	System.out.println(user);
	    	if(user!=null)
	    	return new ResponseEntity<User>(user,HttpStatus.OK);
	    	else {
				// TODO: handle exception
	    		return new ResponseEntity<String>("user is empty",HttpStatus.NOT_FOUND);
			}
	    }
	 
	 @PostMapping("/updateUser")
	  public ResponseEntity<?> updatePersonalInfo(@RequestBody User user){
		 log.info("Inside UserController.updatePersonalInfo()");
//		 log.info("Inside before user userOperationService-->"+user);	    
		 User userOrignal=userService.fetchUser(user.getUserId());
//		 System.out.println("before  userOrignal userOperationService-->"+userOrignal);	    
		 if(user.getUserEmailId()!=null ) {
		  userOrignal.setUserEmailId(user.getUserEmailId());
//		  userOrignal.setUsername(user.getUsername());
		 }
		 if(user.getUsersName()!=null) {
			 userOrignal.setUsersName(user.getUsersName());
		 }
		 if(user.getUserMobNo()!=null) {
			 userOrignal.setUserMobNo(user.getUserMobNo());
		 }
		 if(user.getUserGender()!=null) {
			 userOrignal.setUserGender(user.getUserGender());
		 }
		 if(user.getUserDateOfBirth()!=null) {
			 userOrignal.setUserDateOfBirth(user.getUserDateOfBirth());
		 }
//		 if(user.getPassword()==null || user.getPassword()=="" ||user.getPassword().length()==0) {
//			 
//			 userOrignal.setUserPassword(passwordEncoder.encode(userOrignal.getUserPassword()));
//
//		 }
//		 else {
			 
//			 userOrignal.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
//		 }
		 userOrignal.setRole(Role.USER);
			
		 
		
	    	User userOut=userOperationService.updateUserInfo(userOrignal);
//	    	System.out.println("userOut-->"+userOrignal);	    
	    	if(userOut!=null) {
//	    			return new ResponseEntity<String>("user is updated",HttpStatus.OK);
	    			return new ResponseEntity<>(HttpStatus.OK);
	
	    	}else {
	    		
	    		return new ResponseEntity<String>("user is not updated",HttpStatus.NOT_MODIFIED);
	    		
	    		}
	   }
	 
	 @GetMapping("/screening/{auditorium_id}")
		public ResponseEntity<?> getScreeningByAuditoriumID(@PathVariable("auditorium_id") Integer auditorium_id){
			log.info("Inside UserController.getScreeningByAuditoriumID()");
			
			List<Screening> lis=adminService.getAllScreening();
			if(lis.isEmpty()) {
				return new ResponseEntity<String>("No Scrreining Listed",HttpStatus.NOT_FOUND);
			}else {
				
				Optional<Screening> op=lis.stream().filter(f->f.getAuditorumFk().getAuditoriumId()==auditorium_id)
							.findAny();
				return new ResponseEntity<Screening>(op.get(),HttpStatus.OK);
				
				
			}
		}

	    
   
       
}
