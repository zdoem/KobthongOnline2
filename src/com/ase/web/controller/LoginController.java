package com.ase.web.controller;
import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ase.dao.service.Common;
import com.ase.dao.service.UserService;
import com.ase.dao.service.UserServiceImpl;
import com.ase.model.bean.Account;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;
import com.ase.model.domain.LoginForm;
import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;


@Controller
public class LoginController {
	
	static String  clazzName =  "LoginController";
	static{
		//initial  First Request  configure  datasouce only
		//Use  connection db server type  Connection pool
		Common.setConfigForConnectionPool("", Constant.DataSourceName);
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
	
	@RequestMapping("/login")
	public ModelAndView showLoginForm() {
		System.out.println("-------------showLoginForm  Controller ------------------");				
		//mapping LoginForm.jsp file ,bean name,Object Form
		return new ModelAndView("LoginForm", "command",new LoginForm());
	}
	
	@RequestMapping(value = "/loginAuth", method = RequestMethod.POST)
	public ModelAndView doAuthorizeAction(HttpServletRequest request,@ModelAttribute("LoginForm") LoginForm loginForm, BindingResult result) {	
		 HttpSession session = request.getSession();
		
		Connection conn = null;
		String forward = "redirect:/home.html";	
		try{
			printOutParam(request);			
			System.out.println("ddddddd--->loginAuth.html");	
			System.out.println("loginForm usr :"+loginForm.getUserName());			
			if(session.getAttribute(Constant.SS_USEROBJ)!=null){
				session.removeAttribute(Constant.SS_USEROBJ);
			}			
			//HttpSession session = request.getSession(false);
			System.out.println("-------------loginAuth Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/
			
			if(!Utilize.isValueStrAndObj(loginForm.getUserName()) ||
				!Utilize.isValueStrAndObj(loginForm.getUserPassword())){
				//model.addObject("msg", "กรุณากรอก Username & Password ด้วย");
				return new ModelAndView("LoginForm", "msg","กรุณากรอก Username & Password ด้วย");
			}
			
			/*** New Service or Object Service */
			UserService userService = new UserServiceImpl();
			
			Account userAccount = userService.AuthorizedUserLogIn(conn, loginForm.getUserName(), loginForm.getUserPassword());
			if(userAccount == null){
				System.out.println("===Invalid username and password!==");
				//model.addObject("error", "Invalid username and password!");
				return new ModelAndView("LoginForm", "error","Invalid username or password!");
			}
			//Get Customer profile		
			Customer usrCustomer = userService.GetUserCustomer(conn,Integer.parseInt(userAccount.getAccountId()));
			
			//Get Address from db
			CustomerAddress custAddress = userService.GetUserCustomerAddress(conn, usrCustomer.getCustomerId());
			
			session.setAttribute(Constant.SS_CUSTOMER_PROFILE, usrCustomer);
			session.setAttribute(Constant.SS_USEROBJ, userAccount);
			session.setAttribute(Constant.SS_ADDRESS, custAddress);
			System.out.println("==================Login success===============================");	
		
			//-------------------
			ModelAndView model = new ModelAndView(forward);
			Common.close(conn);
			conn = null;

			return model;
		}catch(Exception e){
			//request.setAttribute("args1", e.getMessage());
		    forward = "error100"; //error100.jsp
		    System.out.println(clazzName+":"+e.getMessage());
		    System.out.println(e.toString());
	
			 try{
				 session.invalidate();//killer session
				 if(conn!=null){
					Common.close(conn);
				 }
				 conn = null;
			    //Close connection
			 }catch(Exception ex){}
			 return new ModelAndView(forward);
	     }finally{
			 try{
				 if(conn!=null){
					Common.close(conn);
				 }
				 conn = null;
			    //Close connection
			 }catch(Exception ex){}
	     }
	}

	@RequestMapping("/LoginTest")
	public ModelAndView doLoginTestAction(HttpServletRequest request) {
			//String message = "Hello World, Spring 3.0!";
		   /*try{			   
		   }catch(Exception e){			   
		   }finally{			   
		   }*/		
			//EchoParamRQ(request);		
			System.out.println("Test Controller...");
			return new ModelAndView("login2", "command", "hello Spring MVC");
			//"fileName.jsp","beanName/AttributeName","Object/List/Container"
	}
	
	
}
