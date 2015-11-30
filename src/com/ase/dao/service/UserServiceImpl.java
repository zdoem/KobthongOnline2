package com.ase.dao.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ase.model.bean.Account;
import com.ase.model.bean.Admin;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;
import com.ase.web.util.Constant;
import com.ase.web.util.EncryptDecrypt;


public class UserServiceImpl implements UserService{
	private static org.apache.log4j.Logger Log = Logger.getLogger(MasterProductTypeImpl.class);
	static String sysName = "KobthongOnline";
	static String clazzName = "UserServiceImpl";	
	
	public Account AuthorizedUserLogIn(Connection conn,String userId, String password) {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isCheckUserId = false; 
		Account  userLogin = null;
		
		EncryptDecrypt Decrypt = new EncryptDecrypt();
		
		try{	
				sql.delete(0, sql.length());
				sql.append(" SELECT account_id, username, password, user_type, user_last_login ")
				   .append(" FROM "+Constant.INSTANT_DB_NAME+".account  WHERE username = ? ");
				System.out.println("-->SQL Get Account :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setString(i++,userId);

				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					userLogin = new Account();
					userLogin.setAccountId(rs.getString("account_id"));
					userLogin.setUserName(rs.getString("username"));
					userLogin.setUserPassword(rs.getString("password"));//Encrypt Text
					userLogin.setUserType(rs.getString("user_type"));
					userLogin.setDateLastLogin(rs.getString("user_last_login"));
					isCheckUserId = true; 
				} // End if rs
				
				if(!isCheckUserId){
					//UserName is incurrect..
					System.out.println("user name is incurrect1...");
					return null;
				}
				
				System.out.println("===>password :"+userLogin.getUserPassword());
				System.out.println("===>password dec:"+Decrypt.DecryptText(userLogin.getUserPassword()));
				
				//Decrypt
				if(isCheckUserId && password.equals(Decrypt.DecryptText(userLogin.getUserPassword()))){
					/* discovery userId in System*/
					System.out.println("Login successfully...");
					return userLogin;
				}else{
					System.out.println("user name is incurrect2...");
					return null;
				}
		   
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":AuthorizedUserLogIn :"+e.toString());
			return null;
		}
		finally{
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public Customer GetUserCustomer(Connection conn,int accountId) {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//boolean isCheckUserId = false;
		Customer   usrCust = null;
		
		try{
				sql.delete(0, sql.length());
				sql.append(" SELECT customer_id, first_name, last_name, telephone, mobile, fax, email, account_id  ")
					.append(" FROM "+Constant.INSTANT_DB_NAME+".customer ")
					.append(" WHERE account_id = ? ");
				System.out.println("SQL Get CUSTOMER :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setInt(i++,accountId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					usrCust = new Customer();
					usrCust.setCustomerId(rs.getInt("customer_id"));
					usrCust.setFirstName(rs.getString("first_name"));
					usrCust.setLastName(rs.getString("last_name"));
					usrCust.setTelephone(rs.getString("telephone"));
					usrCust.setMobile(rs.getString("mobile"));
					usrCust.setFax(rs.getString("fax"));
					usrCust.setEmail(rs.getString("email"));
					usrCust.setAccountId(rs.getInt("account_id"));

				} // End if rs
				
				return usrCust;				
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":GetUserCustomer :"+e.toString());
			return null;
		}
		finally{
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public CustomerAddress GetUserCustomerAddress(Connection conn, int addresId) {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//boolean isCheckUserId = false;
		CustomerAddress   objAddress = null;
		
		try{
				sql.delete(0, sql.length());
				sql.append(" SELECT address_id,address_desc,customer_id  ")
					.append(" FROM "+Constant.INSTANT_DB_NAME+".customer_address ")
					.append(" WHERE customer_id = ? ");
				System.out.println("SQL GetUserCustomerAddress :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setInt(i++,addresId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					objAddress = new CustomerAddress();
					
					objAddress.setAddressId(rs.getString("address_id"));
					objAddress.setAddressDesc(rs.getString("address_desc"));
					objAddress.setCustomerId(rs.getInt("customer_id"));

				} // End if rs
				
				return objAddress;				
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":GetUserCustomerAddress :"+e.toString());
			return null;
		}
		finally{
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int UpdateCustomer(Connection conn, Customer obj) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	int i=1;
        	//System.out.println("##UpdateCustomer ->Starting.");        	 
        	
			/******************************************************/					
       	 	sql.delete(0,sql.length());
       	 	sql.append(" UPDATE "+Constant.INSTANT_DB_NAME+".customer SET  first_name = ?, last_name= ? , mobile = ? ,email = ?  ")
       	 	   .append(" Where  customer_id = ? ");		    
       	 	System.out.println("UpdateCustomer SQL :"+sql.toString());
		    pstmt = conn.prepareStatement(sql.toString()); 
		    pstmt.setString(i++, obj.getFirstName());
		    pstmt.setString(i++, obj.getLastName());		    
		    pstmt.setString(i++, obj.getMobile());
		    pstmt.setString(i++, obj.getEmail());
		    pstmt.setInt(i++,obj.getCustomerId());

		    System.out.println("---Update SQL :"+sql.toString());
		    int intUpd = pstmt.executeUpdate();
		    //System.out.println("---Update Okay..");
			//********************************************************/
		  	//System.out.println("##UpdateCustomer ->end.");				  	 
		  	return intUpd;			  	 
		}catch(Exception e){
			System.out.println("!!UpdateCustomer , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return -1;
		}
		finally{			
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}	
	
	public int UpdateCustomerAddress(Connection conn, CustomerAddress obj) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	int i=1;
        	//System.out.println("##UpdateCustomerAddress ->Starting.");        	 
        	
			/******************************************************/					
       	 	sql.delete(0,sql.length());
       	 	sql.append(" UPDATE "+Constant.INSTANT_DB_NAME+".customer_address SET  address_desc = ?  ")
       	 	   .append(" Where  address_id = ? ");
		    //System.out.println("Update SQL :"+sql.toString());
		    pstmt = conn.prepareStatement(sql.toString()); 
		    pstmt.setString(i++, obj.getAddressDesc());
		    pstmt.setInt(i++, Integer.parseInt(obj.getAddressId()));		    
   
		    System.out.println("---UpdateCustomerAddress SQL :"+sql.toString());
		    int intUpd = pstmt.executeUpdate();
		    //System.out.println("---Update Okay..");
			//********************************************************/
		  	//System.out.println("##UpdateCustomerAddress ->end.");				  	 
		  	return intUpd;			  	 
		}catch(Exception e){
			System.out.println("!!UpdateCustomerAddress , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return -1;
		}
		finally{			
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}

	@Override
	public boolean IsDuplicateUserName(Connection conn, String userName) {
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isResult = false;
		try{		
				//201320210005
				sql.delete(0, sql.length());
				sql.append(" Select username From "+Constant.INSTANT_DB_NAME+".account  Where username = ? ");
				pstmt = conn.prepareStatement(sql.toString()); 
				pstmt.setString(1,userName);
				rs = pstmt.executeQuery();
				System.out.println("IsDuplicateUserName SQL :"+sql.toString());
				if(rs.next()){
					rs.getString("username");
					isResult = true;
				} // End if rs
				
				return isResult;
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":IsDuplicateUserName:"+e.toString());
			System.out.println(" SQL Exception: "+sql.toString());	
			return isResult;
		}
		finally{
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}

	@Override
	public int InsertAccount(Connection conn, Account obj) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	int i=1;
        	//System.out.println("##InsertAccount ->Starting.");        	 
        	EncryptDecrypt  encrypt = new EncryptDecrypt();
			/******************************************************/					
			sql.delete(0, sql.length());
			sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".account ")
					.append(" (username, ")
					.append(" password,  ")
					.append(" user_type  ")
					.append(" )   		")
					.append(" VALUES (?  , ?  , ?) ");
		    System.out.println("InsertAccount SQL :"+sql.toString());
		    pstmt = conn.prepareStatement(sql.toString()); 	    
		    pstmt.setString(i++, obj.getUserName());
		    pstmt.setString(i++, encrypt.EncryptText(obj.getUserPassword()));
		    pstmt.setString(i++, obj.getUserType());

		    //System.out.println("---Insert SQL :"+sql.toString());
		    int intUpd = pstmt.executeUpdate();
		    System.out.println("---InsertAccount Okay..");
			//********************************************************/
		  	//System.out.println("##InsertAccount ->end.");				  	 
		  	return intUpd;			  	 
		}catch(Exception e){
			System.out.println("!!InsertAccount , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return -1;
		}
		finally{			
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}

	@Override
	public int InsertAdmin(Connection conn, Admin obj) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	int i=1;
        	//System.out.println("##InsertAdmin ->Starting.");        	         
			/******************************************************/	        	
			sql.delete(0, sql.length());
			sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".admin ")
					.append(" (first_name, ")
					.append(" last_name,  ")
					.append(" account_id  ")
					.append(" )   		")
					.append(" VALUES (?  , ? ,  ?) ");
		    System.out.println("InsertAdmin SQL :"+sql.toString());
		    pstmt = conn.prepareStatement(sql.toString()); 	    
		    pstmt.setString(i++, obj.getFirstName());
		    pstmt.setString(i++, obj.getLastName());
		    pstmt.setInt(i++, obj.getAccountId());

		    //System.out.println("---Insert SQL :"+sql.toString());
		    int intUpd = pstmt.executeUpdate();
		    System.out.println("---InsertAdmin Okay..");
			//********************************************************/
		  	//System.out.println("##InsertAdmin ->end.");				  	 
		  	return intUpd;			  	 
		}catch(Exception e){
			System.out.println("!!InsertAdmin , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return -1;
		}
		finally{			
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}
	
	@Override
	public int InsertCustomer(Connection conn, Customer obj) {
			StringBuffer sql = new StringBuffer();	
			PreparedStatement pstmt = null;
			ResultSet rs = null;
	        try{
	        	//initial paramter		
	        	int i=1;
	        	//System.out.println("##InsertCustomer ->Starting.");        	         
				/******************************************************/	        	
				sql.delete(0, sql.length());
				sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".customer ")
						.append(" (first_name, ")
						.append(" last_name,   ")
						.append(" telephone , ")
						.append(" mobile  ,   ")
						.append(" fax     ,   ")
						.append(" email    ,  ")
						.append(" account_id  ")				
						.append(" )   		")
						.append(" VALUES (?  , ?  , null  ,  ?  , null , ?  , ?  ) ");
			    System.out.println("InsertCustomer SQL :"+sql.toString());
			    pstmt = conn.prepareStatement(sql.toString()); 	    
			    pstmt.setString(i++, obj.getFirstName());
			    pstmt.setString(i++, obj.getLastName());
			    //null
			    pstmt.setString(i++, obj.getMobile());
			    //null
			    pstmt.setString(i++, obj.getEmail());
			    pstmt.setInt(i++, obj.getAccountId());

			    //System.out.println("---Insert SQL :"+sql.toString());
			    int intUpd = pstmt.executeUpdate();
			    System.out.println("---InsertCustomer Okay..");
				//********************************************************/
			  	//System.out.println("##InsertAdmin ->end.");				  	 
			  	return intUpd;			  	 
			}catch(Exception e){
				System.out.println("!!InsertCustomer , " +sysName+":"+ clazzName + " : " + e.getMessage());
				System.out.println(" SQL Exception: "+sql.toString());		
				return -1;
			}
			finally{			
				//clean up.
				try{
					if(rs!=null){rs.close();}
					if(pstmt!=null){pstmt.close();}
				}catch(Exception e){}
			}
	}

	@Override
	public int InsertAddress(Connection conn, CustomerAddress obj) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	int i=1;
        	//System.out.println("##InsertAddress ->Starting.");        	         
			/******************************************************/	        	
			sql.delete(0, sql.length());
			sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".customer_address ")
					.append(" (address_desc, ")
					.append(" customer_id  ")
					.append(" )   		")
					.append(" VALUES (?  , ?  ) ");
		    System.out.println("InsertAddress SQL :"+sql.toString());
		    pstmt = conn.prepareStatement(sql.toString()); 	    
		    pstmt.setString(i++, obj.getAddressDesc());
		    pstmt.setInt(i++, obj.getCustomerId());

		    //System.out.println("---Insert SQL :"+sql.toString());
		    int intUpd = pstmt.executeUpdate();
		    System.out.println("---InsertAddress Okay..");
			//********************************************************/
		  	//System.out.println("##InsertAdmin ->end.");				  	 
		  	return intUpd;			  	 
		}catch(Exception e){
			System.out.println("!!InsertAddress , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return -1;
		}
		finally{			
			//clean up.
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
			}catch(Exception e){}
		}
	}

	@Override
	public Account GetAccount(Connection conn, String userId) {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//boolean isCheckUserId = false;
		Account   accObj = null;
		
		try{
				sql.delete(0, sql.length());
				sql.append(" SELECT account_id, username, password, user_type  ")
					.append(" FROM "+Constant.INSTANT_DB_NAME+".account ")  
					.append(" WHERE username = ? ");
				System.out.println("SQL GetAccount :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setString(i++,userId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					accObj = new Account();
					accObj.setAccountId(rs.getString("account_id"));
					accObj.setUserName(rs.getString("username"));
					accObj.setUserPassword(rs.getString("password"));
					accObj.setUserType(rs.getString("user_type"));

				} // End if rs
				
				return accObj;				
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":GetAccount :"+e.toString());
			return null;
		}
		finally{
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
