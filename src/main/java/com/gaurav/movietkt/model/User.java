package com.gaurav.movietkt.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String usersName;
	
	@JsonProperty
	private Date userDateOfBirth;

	@JsonProperty
	@Column(unique = true)
	private String userMobNo;
	
	@JsonProperty
	private String userGender;

	@Column(unique = true,nullable = false)
	private String userEmailId;
	
	@Column(unique = true)
	@JsonProperty
	@JsonIgnore
	private String userPassword;
	
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	

	@CreationTimestamp
	@JsonIgnore
	private Date accountCreatedDate;

	@UpdateTimestamp
	@JsonIgnore
	private Date accountLastModifiedDate;
	
//=============================================	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userPassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userEmailId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	 
	 

	



	
	






//	    // created bidirectional relationship
//	    @OneToMany(targetEntity = Reservation.class,cascade = CascadeType.ALL ,mappedBy = "reservationId")
//	    private List<Reservation> reservationIdFk;
}
