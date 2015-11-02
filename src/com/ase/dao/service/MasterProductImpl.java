package com.ase.dao.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ase.model.bean.Product;
import com.ase.model.bean.ProductType;
//import com.ase.model.domain.ProductForm;
import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;


public class MasterProductImpl implements MasterProduct{
	private static org.apache.log4j.Logger Log = Logger.getLogger(MasterProductTypeImpl.class);
	static String sysName = "KobthongOnline";
	static String clazzName = "MasterProductImpl";
	
	
	@Override
	public List listProduct(Connection conn,String limit) {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	//System.out.println("##listProduct ->Starting.");   
        	List  resultList = new ArrayList();
        	Product  obj = null;
			/******************************************************/	       	
			sql.delete(0,sql.length());
			sql.append(" SELECT a.product_id, a.product_name, a.product_size, a.product_desc, a.product_img, ")
			   .append(" a.product_quantity, a.product_color, a.product_price, a.product_sale, a.product_last_update, a.admin_id,  ")
			   .append(" a.product_type_id,b.product_type_name  ")
			   .append(" FROM "+Constant.INSTANT_DB_NAME+".product a,"+Constant.INSTANT_DB_NAME+".product_type b ")
			   .append(" WHERE a.product_type_id = b.product_type_id ")
			   .append(" Order by product_last_update desc   "+limit);


			System.out.println("listProduct- SQL :"+sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();	
			while(rs.next()){	
				obj = new Product();
				obj.setProductId(rs.getString("product_id"));
				obj.setProductName(rs.getString("product_name"));
				obj.setProductSize(rs.getString("product_size"));
				obj.setProductDesc(Utilize.SubString128Char(rs.getString("product_desc")));
				obj.setProductImageName(rs.getString("product_img"));
				obj.setProductQuantity(rs.getInt("product_quantity"));
				obj.setProductColor(rs.getString("product_img"));
				obj.setProductPrice(rs.getDouble("product_price"));
				obj.setProductSale(rs.getDouble("product_sale"));
				obj.setProductLastUpdate(rs.getString("product_last_update"));
				obj.setAdminId(rs.getString("admin_id"));
				obj.setTypeProductId(rs.getString("product_type_id"));
				obj.setTypeProductName(rs.getString("product_type_name"));
				
				resultList.add(obj);
			}
			rs.close();				
			//********************************************************/
		  	System.out.println("##listProduct ->end.");				  	 
		  	return resultList;			  	 
		}catch(Exception e){
			System.out.println("!!!listProduct , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return null;
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
	public List listProductByType(Connection conn, String productTypeId)
			throws Exception {
		StringBuffer sql = new StringBuffer();	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	//initial paramter		
        	//System.out.println("##listProductByType ->Starting.");   
        	List  resultList = new ArrayList();
        	Product  obj = null;
			/******************************************************/	       	
			sql.delete(0,sql.length());
			sql.append(" SELECT a.product_id, a.product_name, a.product_size, a.product_desc, a.product_img, ")
			   .append(" a.product_quantity, a.product_color, a.product_price, a.product_sale, a.product_last_update, a.admin_id,  ")
			   .append(" a.product_type_id,b.product_type_name  ")
			   .append(" FROM "+Constant.INSTANT_DB_NAME+".product a,"+Constant.INSTANT_DB_NAME+".product_type b ")
			   .append(" WHERE a.product_type_id = b.product_type_id ")
			   .append(" and a.product_type_id = ? ")
			   .append(" Order by product_last_update desc   ");


			System.out.println("listProduct by TypeID- SQL :"+sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1,Integer.parseInt(productTypeId));
			
			rs = pstmt.executeQuery();	
			while(rs.next()){	
				obj = new Product();
				obj.setProductId(rs.getString("product_id"));
				obj.setProductName(rs.getString("product_name"));
				obj.setProductSize(rs.getString("product_size"));
				obj.setProductDesc(Utilize.SubString128Char(rs.getString("product_desc")));
				obj.setProductImageName(rs.getString("product_img"));
				obj.setProductQuantity(rs.getInt("product_quantity"));
				obj.setProductColor(rs.getString("product_img"));
				obj.setProductPrice(rs.getDouble("product_price"));
				obj.setProductSale(rs.getDouble("product_sale"));
				obj.setProductLastUpdate(rs.getString("product_last_update"));
				obj.setAdminId(rs.getString("admin_id"));
				obj.setTypeProductId(rs.getString("product_type_id"));
				obj.setTypeProductName(rs.getString("product_type_name"));
				
				resultList.add(obj);
			}
			rs.close();				
			//********************************************************/
		  	System.out.println("##listProductByType ->end.");				  	 
		  	return resultList;			  	 
		}catch(Exception e){
			System.out.println("!!!listProductByType , " +sysName+":"+ clazzName + " : " + e.getMessage());
			System.out.println(" SQL Exception: "+sql.toString());		
			return null;
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
	public Product GetProductDesc(Connection conn, String productId)
			throws Exception {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//boolean isCheckUserId = false;
		Product   obj = null;
		
		try{
			sql.delete(0,sql.length());
			sql.append(" SELECT a.product_id, a.product_name, a.product_size, a.product_desc, a.product_img, ")
			   .append(" a.product_quantity, a.product_color, a.product_price, a.product_sale, a.product_last_update, a.admin_id,  ")
			   .append(" a.product_type_id,b.product_type_name  ")
			   .append(" FROM "+Constant.INSTANT_DB_NAME+".product a,"+Constant.INSTANT_DB_NAME+".product_type b ")
			   .append(" WHERE a.product_type_id = b.product_type_id ")
			   .append(" and a.product_id = ? ");

				System.out.println("SQL GetProductDesc :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setString(i++,productId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					obj = new Product();
					obj.setProductId(rs.getString("product_id"));
					obj.setProductName(rs.getString("product_name"));
					obj.setProductSize(rs.getString("product_size"));
					obj.setProductDesc(Utilize.SubString128Char(rs.getString("product_desc")));
					obj.setProductImageName(rs.getString("product_img"));
					obj.setProductQuantity(rs.getInt("product_quantity"));
					obj.setProductColor(rs.getString("product_color"));
					obj.setProductPrice(rs.getDouble("product_price"));
					obj.setProductSale(rs.getDouble("product_sale"));
					obj.setProductLastUpdate(rs.getString("product_last_update"));
					obj.setAdminId(rs.getString("admin_id"));
					obj.setTypeProductId(rs.getString("product_type_id"));
					obj.setTypeProductName(rs.getString("product_type_name"));

				} // End if rs
				
				return obj;				
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":GetProductDesc :"+e.toString());
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
