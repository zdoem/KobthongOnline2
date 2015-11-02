package com.ase.dao.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ase.model.bean.Customer;
import com.ase.model.bean.ProductType;
//import com.ase.model.domain.ProductTypeForm;
import com.ase.web.util.Constant;

public class MasterProductTypeImpl  implements MasterProductType {
	private static org.apache.log4j.Logger Log = Logger.getLogger(MasterProductTypeImpl.class);
	static String sysName = "KobthongOnline";
	static String clazzName = "MasterProductTypeImpl";	

	@Override
	public List listProductType(Connection conn) throws Exception {
		// TODO Auto-generated method stub
		ResultSet rs 		= 	null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		ProductType  obj = null;
		List<ProductType> result = new ArrayList<ProductType>();				
		try{	 
			Log.debug("-->Query.");		
			sql.delete(0, sql.length());
			sql.append(" SELECT product_type_id, product_type_name, product_type_img  ")
			   .append(" FROM "+Constant.INSTANT_DB_NAME+".product_type ")
			   .append(" ORDER BY product_type_name ASC ");	
			Log.debug("SQL Query:"+sql.toString());		
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();				
			Log.debug("--->execute SQL Query.");			    	  
			//int c=0;
		    while(rs.next()){	 			  
				obj = new  ProductType();
				obj.setProductTypeId(rs.getString("product_type_id"));
				obj.setProductTypeName(rs.getString("product_type_name"));
				obj.setProductTypeImageName(rs.getString("product_type_img"));
				result.add(obj);
				//c++;
			}	 		  
			//Log.debug("get UserInfo FetchSize :"+c);
			Log.debug("--->Query completed.");
			return result;
		}catch(Exception e){
			System.out.println("listProductType , " +sysName+":"+ clazzName + " : " + e.getMessage());
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
	public ProductType GetProductType(Connection conn, String productTypeId)
			throws Exception {
		int i = 1;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//boolean isCheckUserId = false;
		ProductType   obj = null;
		
		try{
				sql.delete(0, sql.length());
				sql.append(" SELECT product_type_id, product_type_name, product_type_img, product_type_desc, last_update, admin_id   ")
					.append(" FROM "+Constant.INSTANT_DB_NAME+".product_type ")
					.append(" WHERE product_type_id = ? ");
				System.out.println("SQL GetProductType :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
			    pstmt.setInt(i++,Integer.parseInt(productTypeId));
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					obj = new ProductType();
					obj.setProductTypeId(rs.getString("product_type_id"));
					obj.setProductTypeName(rs.getString("product_type_name"));
					obj.setProductTypeImageName(rs.getString("product_type_img"));
					obj.setProductTypeDesc(rs.getString("product_type_desc"));
					obj.setProductTypeLastUpdate(rs.getString("last_update"));
					obj.setAdminId(rs.getString("admin_id"));

				} // End if rs
				
				return obj;				
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":GetProductType :"+e.toString());
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
