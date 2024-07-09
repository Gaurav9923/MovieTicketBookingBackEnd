package com.gaurav.movietkt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaurav.movietkt.Dao.IUserRepo;
import com.gaurav.movietkt.SecurityConfig.JwtService;
import com.gaurav.movietkt.model.Role;
import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.model.authModel.JwtAuthenticationResponse;
import com.gaurav.movietkt.model.authModel.SignInRequest;
import com.gaurav.movietkt.model.authModel.SignUpRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class IAuthenicationServiceImpl implements IAuthenticationService {
	
	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	
	public User signUp(SignUpRequest signUpRequest) {
		
		log.info("Inside IAuthenicationServiceImpl.signUp()");
		User user=new User();
		user.setUsersName(signUpRequest.getUsersName());
		user.setUserEmailId(signUpRequest.getUserEmailId());
		user.setRole(Role.USER);
		user.setUserPassword(passwordEncoder.encode(signUpRequest.getUserPassword()));
		
		 return  userRepo.save(user);

		
		
	}
	
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
		log.info("Inside IAuthenicationServiceImpl.signIn()");
		log.info(signInRequest.getEmail()+"===>"+signInRequest.getPassword());
		 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
		 
		 var user=userRepo.findByUserEmailId(signInRequest.getEmail())
				 			.orElseThrow(
				 					()->new IllegalArgumentException("provided email/ password is wrong")	
				 					);
		 
//		 System.out.println("==>"+user);
		 
		 var jwtToken= jwtService.generateToken(user);
//		 var jwtRefreshToken= jwtService.generateRefreshToken(new HashMap<>(),user);
		 
		 JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
		 jwtAuthenticationResponse.setToken(jwtToken);
		 jwtAuthenticationResponse.setUserId(user.getUserId());
		 
		 //		 jwtAuthenticationResponse.setRefreshToken(jwtRefreshToken);
		 
		 return jwtAuthenticationResponse;
		 
	}
	
	
//	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//		
//		String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
//		Optional<User> user=userRepo.findByUserEmailId(userEmail);
//		if(user.isEmpty()) {			
//	         throw new NotFoundException("user with email not found to generate refresh token");
//		}
//		
//		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user.get())) {
//			 
//			 var jwtToken= jwtService.generateToken(user.get());
//			
//			 JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
//			 jwtAuthenticationResponse.setToken(jwtToken);
//			 jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
//			 
//			 return jwtAuthenticationResponse;
//			 
//		}
//		
//		return null;
//		
//		
//	}

 

	

}
