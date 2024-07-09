package com.gaurav.movietkt.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gaurav.movietkt.model.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

	 // ApplicationConfig load username(email) for authentication
	 @Autowired
	 private ApplicationConfig applicationConfig;
	  
  	private final JwtAuthenticationFilter jwtAuthFilter;



	   

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	log.info("Inside SecurityConfiguration.securityFilterChain()");
			http.csrf(AbstractHttpConfigurer::disable)
	    		
	    		.authorizeHttpRequests(auth -> auth
	    				.requestMatchers("/api/user/moviesList").permitAll()	    	       
	    				.requestMatchers("/api/user/screening/**").permitAll()	    	       
	    				.requestMatchers("/api/user/getMovie/**").permitAll()	    	       
	    				.requestMatchers("/api/user/moviesSearchList/**").permitAll()	    	       
	    				.requestMatchers("/api/book/bookedSeat/**").permitAll()	    	       
	    				.requestMatchers("/api/book/selectSeat/**").permitAll()	    	       
//	    				.requestMatchers("/api/book/confirmSeats/**").permitAll()	    	       
	    				.requestMatchers("/api/user/screeningList").permitAll()	    	       
	    				.requestMatchers("/api/admin/getAuditorium/**").permitAll()	    	       
	    				.requestMatchers("/api/admin/screening/**").permitAll()	    	       
	    	            .requestMatchers("/api/auth/*").permitAll()	    	       
	    	            .requestMatchers("/api/user/*").hasAnyAuthority(Role.USER.name())
	    	            .requestMatchers("/api/book/*").hasAnyAuthority(Role.USER.name())
	    	            .requestMatchers("/api/admin/*").hasAnyAuthority(Role.ADMIN.name())
	    	            .anyRequest().authenticated()
	    	            
	    				)   
	    		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    		.authenticationProvider(authenticationProvider())
	    		.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
	    	
	    	return http.build();
	    	
	      
	  
	    }
	
	    
	    @Bean
		public AuthenticationProvider authenticationProvider() {
	    	log.trace("Inside  SecurityConfiguration.authenticationProvider()");
			DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(applicationConfig.userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());
			
			log.trace("Inside ApplicationConfig.authenticationProvider()==>"+authProvider);
			
			return authProvider;
		}
		
		@Bean
		public AuthenticationManager  authenticationManager(AuthenticationConfiguration config) throws Exception {
			log.info("Inside ApplicationConfig.authenticationManager()");
			return config.getAuthenticationManager();
		}
		
		
		@Bean
		public  PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
}
