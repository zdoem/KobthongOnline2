package com.ase.dao.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;

public class GenerateAutoID  {
	static String  clazzName = "GenerateAutoID";
	private static org.apache.log4j.Logger Log = Logger.getLogger(MasterProductTypeImpl.class);
	static String sysName = "KobthongOnline";

	public static String getAutoIdFromTable(Connection conn, String fieldName,String tableName){
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String maxId = "";
		String tempId = "";
		try{
				sql.delete(0, sql.length());
				sql.append("  select max("+fieldName+") as maxid from  "+Constant.INSTANT_DB_NAME+"."+tableName);
				pstmt = conn.prepareStatement(sql.toString()); 
				//pstmt.setString(1, fieldName);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					//0001
					maxId =rs.getString("maxid");
					//System.out.println(rs.getInt("maxid"));
				} // End if rs
				
				System.out.println("-->Max ID:"+maxId);
				String currentID = Utilize.NowByCalendar("yyyy-MM-dd").replaceAll("-", "");				
				if(maxId == null || maxId.equals("")){
				   return currentID+"0001";	
				}
				
				String dateID = maxId;				
				if(maxId.length()>7){
					dateID = maxId.substring(0,8);
				}
				System.out.println("-->"+dateID);
				System.out.println("-->"+currentID);
				
				if(dateID.equals(currentID)){
					int x = Integer.parseInt(maxId.substring(8,maxId.length()));
					System.out.println("NextID :"+x);
					x++;
					System.out.println("---->Next ID X:"+currentID+Utilize.GenNextId(x));	
					tempId = currentID+Utilize.GenNextId(x);
				}else{
					System.out.println("---->New ID Y:"+currentID+"0001");
					tempId = currentID+"0001";
				}
				return tempId;
		}catch(Exception e){
			e.fillInStackTrace();
			System.out.println(clazzName+":getAutoIdFromTable :"+e.toString());
			return "0";
		}
		finally{
			try {
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
}
