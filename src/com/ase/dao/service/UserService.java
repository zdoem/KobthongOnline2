package com.ase.dao.service;

import java.sql.Connection;

import com.ase.model.bean.Account;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;


public interface UserService {
	public Account AuthorizedUserLogIn(Connection conn,String userId, String password); 
	public Customer  GetUserCustomer(Connection conn,int accountId);
	public CustomerAddress GetUserCustomerAddress(Connection conn,int addresId);
	public int UpdateCustomer(Connection conn, Customer obj);
	public int UpdateCustomerAddress(Connection conn, CustomerAddress obj);
}
