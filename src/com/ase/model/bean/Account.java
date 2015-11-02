package com.ase.model.bean;

public class Account {
	private String accountId;
	private String userName;
	private String userPassword;
	private String userType;
	private String dateLastLogin;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	
}
