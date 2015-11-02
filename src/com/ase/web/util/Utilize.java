package com.ase.web.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

public class Utilize {
	/**********
	 * 2015.10.23
	 *  by pradoem
	 *  project : DB_ASE
	 */
	public static boolean  verifyLogin(HttpSession session) throws Exception {		
		  //HttpSession session = req.getSession();
		  boolean isLogin = false;
		  //true  = Not' login
		  //false = session login success   
		  if (session == null) {
			  System.out.println("******* (session == null) ********");
		        //** Redirect user to login page if// there's no session.*//*
		        //response.sendRedirect(request.getContextPath()+"/login.jsp");
		        // return false;
			    isLogin = true;
		   }
		  
		  Object obj = session.getAttribute(Constant.SS_USEROBJ);
		  //session.getAttribute("USER");
		  if (obj == null) {
		    	System.out.println("----->UserLogin is null");
		    	System.out.println("******* (obj == null)  ********");
		        //** Redirect user to login page if// there's no session.*//*
		        //response.sendRedirect(request.getContextPath()+"/login.jsp");
		    	isLogin = true;
		   }	
		  return isLogin;
	}	
	
	public static String getThaiCurrentYYYYMMDD(){
		//Date format
	  	Date date = Calendar.getInstance().getTime();
	  	 // Display a date in day, month, year format
	  	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	  	String today = formatter.format(date);
	  	String [] tempDate ;
	  	tempDate = today.split("/");
	  	int yyy = Integer.parseInt(tempDate[2])+543;
	  	
	  return	yyy+"-"+tempDate[1]+"-"+tempDate[0];
  }
	
 public static boolean isValueStrAndObj(String str) throws Exception{
		if ((str == null) || str.equals("")) {
			 return false;
		}else{
			 return true;
		 }
	}	
	

  public static String SubString128Char(String str){
		if ((str == null) || str.equals("")) {
			return str;
		}else{
			if(str.length()>128){
				String temp = str.substring(0,128);
				return temp; 
			}else{
				return str;
			}	
		}
	}
	
  public static String replaceNull(String s) {
		if (s == null) {
		  s = "";
		}
		return s;
	}	
	//------->>GenID
    public static String GenNextId(int b){
		        String temp=""+b;
		        String newSp_id;
		        switch(temp.length()){ 
		          // case 1: newSp_id="00000"+temp; break; // case 2: newSp_id="0000"+temp; break; //case 1: newSp_id="000"+temp; break;
		           case 1: newSp_id="00"+temp; break;
		           case 2: newSp_id="0"+temp; break;
		           default:newSp_id=temp;
		        }
		      return newSp_id;
   }
	public static String NowByCalendar(String dateFormat) {
		    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		    return sdf.format(cal.getTime());
	 }

}
