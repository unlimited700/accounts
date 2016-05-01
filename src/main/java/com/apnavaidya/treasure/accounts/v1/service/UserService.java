package com.apnavaidya.treasure.accounts.v1.service;

import com.apnavaidya.treasure.accounts.dto.UserLoginRequest;
import com.apnavaidya.treasure.accounts.dto.UserLoginResponse;
import com.apnavaidya.treasure.accounts.dto.UserSignUpRequest;
import com.apnavaidya.treasure.accounts.dto.UserSignUpResponse;

public interface UserService {

	public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest);
	
	public UserLoginResponse login(UserLoginRequest userLoginRequest);
	
	public String getToken(Long userId) ;
}
