package com.gaurav.movietkt.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.model.authModel.JwtAuthenticationResponse;
import com.gaurav.movietkt.model.authModel.SignInRequest;
import com.gaurav.movietkt.model.authModel.SignUpRequest;
import com.gaurav.movietkt.service.IAuthenticationService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins  = "*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	 @PostMapping("/signUp")
	    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
		
		 log.info("SignUp request=>"+signUpRequest);
		     User user= authenticationService.signUp(signUpRequest);
		     
	    	return  new ResponseEntity<User>(user,HttpStatus.OK);
	    }
	    
	    @PostMapping("/signIn")
	    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){
	    	
	    	JwtAuthenticationResponse jwtAuthenticationResponse=authenticationService.signIn(signInRequest);
	    	log.info("JWT LOGIN RESPONSE=>"+jwtAuthenticationResponse);
	    	return  new ResponseEntity<JwtAuthenticationResponse>(jwtAuthenticationResponse,HttpStatus.OK);
	    	
	    }
//	    @PostMapping("/refresh")
//	    public ResponseEntity<?> signIn(@RequestBody RefreshTokenRequest refreshTokenRequest){
//	    	JwtAuthenticationResponse jwtAuthenticationResponse=authenticationService.refreshToken(refreshTokenRequest);
//	    	System.out.println(jwtAuthenticationResponse);
//	    	return  new ResponseEntity<JwtAuthenticationResponse>(jwtAuthenticationResponse,HttpStatus.OK);
//	    	
//	    }
}
