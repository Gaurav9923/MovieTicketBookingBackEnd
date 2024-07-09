package com.gaurav.movietkt.model.authModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

	
	private String usersName;
	private String userEmailId;
	private String userPassword;
}
