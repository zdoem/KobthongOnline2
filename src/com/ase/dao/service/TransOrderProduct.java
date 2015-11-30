package com.ase.dao.service;

import java.sql.Connection;
import java.util.List;

public interface TransOrderProduct {
	public int InsertTransOrder(Connection conn, List arrList, String INVOID_ID,int customerId,int addressId,int adminId) throws Exception;
	//public boolean updateTransPayin(PaymentBean obj) throws Exception;
 
}
