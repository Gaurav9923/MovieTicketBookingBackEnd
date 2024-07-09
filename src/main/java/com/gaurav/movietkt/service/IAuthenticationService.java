package com.gaurav.movietkt.service;

import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.model.authModel.JwtAuthenticationResponse;
import com.gaurav.movietkt.model.authModel.SignInRequest;
import com.gaurav.movietkt.model.authModel.SignUpRequest;

public interface IAuthenticationService {

	public User signUp(SignUpRequest signUpRequest);
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest);
//	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;
}
