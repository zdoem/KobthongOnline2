package com.ase.web.controller;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ase.web.util.Constant;

@Controller
public class LogoutController {
	
	static String  clazzName =  "LogoutController";
	static{
		//initial  First Request  configure  datasouce only
		//Use  connection db server type  Connection pool
		//Common.setConfigForConnectionPool("", Constant.DataSourceName);
	}
	
	//Show all parameter All by pass Request query.
	private static void printOutParam(HttpServletRequest request){
		String paramNames = "";
		System.out.println("---------[ Parameter List] ------------");
		for(Enumeration e = request.getParameterNames();e.hasMoreElements(); ){
			paramNames = (String)e.nextElement();
			System.out.println(paramNames+" = "+request.getParameter(paramNames));
		}		
		System.out.println("---------- [Parameter List] -----------");		
	}

	@RequestMapping("/Logout")
	public ModelAndView doLogoutAction(HttpServletRequest request) {
		 HttpSession session = request.getSession();			
			String forward = "redirect:/home.html";	
			try{			
				printOutParam(request);							
				System.out.println("xxxxxxxxxxx--->Logout.html");	
				//HttpSession session = request.getSession(false);
				if(session.getAttribute(Constant.SS_USEROBJ)!=null){
					//**************
					Object obj =  session.getAttribute(Constant.SS_USEROBJ);					
					//session.removeAttribute(Constant.SS_USEROBJ);
					synchronized(session) {
						/****************
						* Clean Up session
						************* */
						session.invalidate();
						System.out.println("--->Clean Up session all.");
					}
				}				
				System.out.println("==================Logout success===============================");	
				ModelAndView model = new ModelAndView(forward);
				return model;
			}catch(Exception e){
				//request.setAttribute("args1", e.getMessage());
			    forward = "error100"; //error100.jsp
			    System.out.println(clazzName+":"+e.getMessage());
			    System.out.println(e.toString());
				 return null;
		    }finally{
		    }
	}
}
