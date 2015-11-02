package com.ase.web.controller;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ase.dao.service.Common;
import com.ase.dao.service.MasterProduct;
import com.ase.dao.service.MasterProductImpl;
import com.ase.dao.service.MasterProductType;
import com.ase.dao.service.MasterProductTypeImpl;
import com.ase.model.domain.LoginForm;
import com.ase.web.util.Constant;


@Controller
public class HomeController {
	
	static String  clazzName =  "HomeController";
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

	//path URL
	@RequestMapping("/home")
	public ModelAndView doHomeAction(HttpServletRequest request) {		
		Connection conn = null;
		String forward = "";
		List productTypeList = null;
		List productList = null;
		try{
			//printOutParam(request);
			//HttpSession session = request.getSession(false);
			System.out.println("-------------HOME,Form Load Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/
		    
			MasterProductType productTypeService = new MasterProductTypeImpl();
			MasterProduct   productService = new MasterProductImpl();
			/***************************
			*For  Logic
			**************************/
			productTypeList = productTypeService.listProductType(conn);
			System.out.println("productTypeList====="+productTypeList.size());
			
			productList = productService.listProduct(conn,Constant.LIMIT_5);
			System.out.println("productList====="+productList.size());

			/***********************************/			
			//"home.jsp","beanName/AttributeName","Object/List/Container"
			//return new ModelAndView(forward, "productTypeList",productTypeList);
			forward = "home"; //home.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject("productList",productList);
			System.out.println("================================");
			
			Common.close(conn);
			conn = null;
			return model;
		}catch(Exception e){
			//request.setAttribute("args1", e.getMessage());
		    forward = "error100"; //error100.jsp
		    System.out.println(clazzName+":"+e.getMessage());
		    System.out.println(e.toString());
			 try{
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
	
	@RequestMapping("/register")
	public ModelAndView showLoginForm() {
		System.out.println("-------------showRegisterForm  Controller ------------------");				
		//mapping LoginForm.jsp file ,bean name,Object Form
		return new ModelAndView("registerForm", "commandRegForm",new LoginForm());
	}
	
	

	//path URL
	@RequestMapping("/homeTest")
	public ModelAndView doHomeTestAction(HttpServletRequest request) {
			//String message = "Hello World, Spring 3.0!";
		   /*try{			   
		   }catch(Exception e){			   
		   }finally{			   
		   }*/		
			//EchoParamRQ(request);		
			System.out.println("Test Controller...");
			return new ModelAndView("home_test", "command", "hello Spring MVC");
			//"fileName.jsp","beanName/AttributeName","Object/List/Container"
	}
	
}
