package com.paf.n3bg4.models;

import com.paf.n3bg4.models.Enums.UserType;

public class AuthResponse {
	
	private boolean isAuthenticate;
	private UserType userType;
	
	public boolean getIsAuthenticate() {
		return isAuthenticate;
	}

	public void setIsAuthenticated(boolean isAuthenticate) {
		this.isAuthenticate = isAuthenticate;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}