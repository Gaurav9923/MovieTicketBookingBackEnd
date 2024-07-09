package com.gaurav.movietkt.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Role;
import com.gaurav.movietkt.model.User;

public interface IUserRepo extends JpaRepository<User, Integer> {
	  public Optional<User> findByUserEmailId(String userEmailId);
	  
	  public List<User> findByRole(Role role);
}
