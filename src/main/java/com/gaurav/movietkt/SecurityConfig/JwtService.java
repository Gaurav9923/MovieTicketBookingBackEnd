package com.gaurav.movietkt.SecurityConfig;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.el.parser.AstString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
	
	//hexadecimal 256 bit key auto generated
	private static final String SECREAT_KEY="c638f28087768308af01349f7317b959b77302336aed749d768ea4f703ca2e76";
	
	public String generateToken(UserDetails userDetails) {
		//generate token without any extra claims
		log.info("Inside JwtService.generateToken()");
		return generateToken(new HashMap<>(),userDetails);
	}
	//1000*60*60*24
	public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {
		
		log.info("Inside JwtService.generateRefreshToken()");
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails) {
		log.info("Inside JwtService.generateToken()");
		
		extraClaims.put("ROLE", userDetails.getAuthorities());
		System.out.println(userDetails.getAuthorities());
		
		log.info(userDetails.getUsername()+"===>"+userDetails.getAuthorities());
		return Jwts
			.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis()+86400000))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}
	
	public boolean isTokenValid(String token ,UserDetails userDetails) {
		log.info("Inside JwtService.isTokenValid()");
		String username=extractUsername(token);
		
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public String extractUsername(String jwtToken) {
		//getSubject() contains username/userEmail 
		return extractClaim(jwtToken, Claims::getSubject);
	}
	
	
	public <T> T extractClaim(String token,Function<Claims,T>claimResolver) {
		final Claims claim=extractAllClaims(token);
		
		return claimResolver.apply(claim);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts
			   .parserBuilder()
			   .setSigningKey(getSigningKey())
			   .build()
			   .parseClaimsJws(token)
			   .getBody();
	}

	private Key getSigningKey() {
		
		byte[] keyBytes=Decoders.BASE64.decode(SECREAT_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	

}
