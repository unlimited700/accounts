package com.apnavaidya.treasure.accounts.dto;

import com.apnavaidya.treasure.accounts.Enum.UserStatus;

public class UserVerifyRequest {

	private UserStatus status;
	private String email;

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
