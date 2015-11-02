package com.ase.model.domain;

public class LoginForm {
	private String userId;
	private String userName;
	private String userPassword;
	private String userType;
	private String dateLastLogin;
	private String cmd;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDateLastLogin() {
		return dateLastLogin;
	}
	public void setDateLastLogin(String dateLastLogin) {
		this.dateLastLogin = dateLastLogin;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
