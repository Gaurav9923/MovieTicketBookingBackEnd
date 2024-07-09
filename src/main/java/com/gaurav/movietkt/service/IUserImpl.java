package com.gaurav.movietkt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.movietkt.Dao.IUserRepo;
import com.gaurav.movietkt.Exception.UserException.NotFoundException;
import com.gaurav.movietkt.model.User;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class IUserImpl implements IUser {
	@Autowired
	private IUserRepo userRepo;
	
	
	
	 public IUserImpl(IUserRepo userRepo) {
	        this.userRepo = userRepo;
	  }
	
	
	
	
	@Override
	public String registerUser(User user) {
//		String pass=;
//		user.setUserPassword(encoder.encode(user.getUserPassword()));
		log.info("Inside IUserImpl.registerUser()");
		if(userRepo.save(user).getUserEmailId()==null ||  userRepo.save(user).getUserEmailId()=="")
            throw new NotFoundException("User Is already exists");
		return userRepo.save(user).getUsersName();
	}


	@Override
	public User fetchUser(Integer uid) {
		log.info("Inside IUserImpl.fetchUser()");
		 if(userRepo.findById(uid).isEmpty())
	            throw new NotFoundException("Requested User does not exist");
		Optional<User> op=userRepo.findById(uid);
		
		return  op.get();
	}


	@Override
	public List<User> fetchAllUser() {
		// TODO Auto-generated method stub
		
		log.info("Inside IUserImpl.fetchAllUser()");
		if(userRepo.findAll().isEmpty())
            throw new NotFoundException("No user Exists");
		return userRepo.findAll();
	}




	

}
