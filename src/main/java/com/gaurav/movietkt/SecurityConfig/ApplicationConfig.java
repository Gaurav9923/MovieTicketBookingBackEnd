package com.gaurav.movietkt.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gaurav.movietkt.Dao.IUserRepo;
import com.gaurav.movietkt.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
		
	    @Autowired
		private final IUserRepo userRepo;
		
	    
	    //load user data for authentication
		@Bean
		public UserDetailsService userDetailsService() {
			return username->{
				 try { log.info("Inside ApplicationConfig.userDetailsService()-->"+username);
			            User user = userRepo.findByUserEmailId(username)
			                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
			           
			            log.info("Inside ApplicationConfig.userDetailsService()==>"+user);
			            return user;
			        } catch (UsernameNotFoundException e) {
			            // Handle the exception as needed, for example, log it or wrap it in a custom exception
			        	log.warn("ApplicationConfig.userDetailsService():: username not found");
			            throw new UsernameNotFoundException("User not found with email: " + username, e);
			        }
			};
				
					
			
		}
		
		
}
