package com.gaurav.movietkt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gaurav.movietkt.Dao.IUserRepo;
import com.gaurav.movietkt.model.Role;
import com.gaurav.movietkt.model.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BackEnd01Application  implements CommandLineRunner{
	
	

	
	@Autowired
	private IUserRepo userRepo;
	
	public static void main(String[] args) {
		log.info("Inside BackEnd01Application.main()");
		SpringApplication.run(BackEnd01Application.class, args);
	}

	
	//perform initial task create admin if not exist
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("BackEnd01Application.run()");
		System.out.println("Application Started.....");
		
		List<User> adminAccount=userRepo.findByRole(Role.ADMIN);
		
		if(adminAccount.isEmpty()) {
			User user=new User();
			user.setUsersName("admin");
			user.setUserEmailId("admin@gmail.com");
			user.setRole(Role.ADMIN);
			user.setUserGender("M");
			user.setUserMobNo("9923625792");
			user.setUserPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepo.save(user);
		}
		
	}

}
