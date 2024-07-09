package com.gaurav.movietkt.model.authModel;

import lombok.Data;

@Data
public class SignInRequest {
		private String email;
		private String password;
}
