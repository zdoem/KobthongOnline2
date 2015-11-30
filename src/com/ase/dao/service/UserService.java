package com.ase.dao.service;

import java.sql.Connection;

import com.ase.model.bean.Account;
import com.ase.model.bean.Admin;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;


public interface UserService {
	public Account AuthorizedUserLogIn(Connection conn,String userId, String password); 
	public Account GetAccount(Connection conn,String userId);
	public Customer GetUserCustomer(Connection conn,int accountId);
	public CustomerAddress GetUserCustomerAddress(Connection conn,int addresId);
	public int UpdateCustomer(Connection conn, Customer obj);
	public int UpdateCustomerAddress(Connection conn, CustomerAddress obj);
	public boolean IsDuplicateUserName(Connection conn, String userName);
	public int InsertAccount(Connection conn, Account obj); 
	public int InsertAdmin(Connection conn, Admin obj);
	public int InsertCustomer(Connection conn, Customer obj); 
	public int InsertAddress(Connection conn, CustomerAddress obj); 
}
