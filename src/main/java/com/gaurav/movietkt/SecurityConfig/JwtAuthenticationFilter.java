package com.gaurav.movietkt.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	

	
	@Autowired
	private JwtService jwtService;    //method in this custom class will extract email from jwt token
	
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)throws ServletException, IOException {
		
		
		log.info("Inside JwtAuthenticationFilter.doFilterInternal()");
		final String authHeader=request.getHeader("Authorization");
		
		final String jwt;
		
		final String userEmail;    //extract email from received token
		
		if(authHeader==null || !authHeader.startsWith("Bearer "))		{
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt=authHeader.substring(7);
		userEmail=jwtService.extractUsername(jwt);   //here not user but email will be extracted
		
		if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null) {
			//here user is not authenicated so we do authentication 
			System.out.println("JwtAuthenticationFilter.doFilterInternal()2");
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
			
			if(jwtService.isTokenValid(jwt, userDetails)) {
				System.out.println("JwtAuthenticationFilter.doFilterInternal()3");
				
					SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					
					System.out.println("JwtAuthenticationFilter.doFilterInternal()4"+userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					securityContext.setAuthentication(token);
					SecurityContextHolder.setContext(securityContext);
					
				
			
				
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
