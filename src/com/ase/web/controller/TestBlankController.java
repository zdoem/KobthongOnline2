package com.ase.web.controller;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * date:2015.10.19
 * author: pradoem wongkraso
 * contact : go2doem@gmail.com,destar_@hotmail.com
 * description: 
 * */

@Controller
public class TestBlankController {
	
	//Show all parameter All by pass Request query.
	private static void EchoParamRQ(HttpServletRequest request){
		String paramNames = "";
		System.out.println("---------[ Parameter List] ------------");
		for(Enumeration e = request.getParameterNames();e.hasMoreElements(); ){
			paramNames = (String)e.nextElement();
			System.out.println(paramNames+" = "+request.getParameter(paramNames));
		}		
		System.out.println("---------- [Parameter List] -----------");		

	}
	
	//path URL
	@RequestMapping("/test")
	public ModelAndView doHomeAction(HttpServletRequest request) {
		//String message = "Hello World, Spring 3.0!";
		
		EchoParamRQ(request);
		
		System.out.println("Test Controller...");
		return new ModelAndView("blank", "command", "hello Spring MVC");
		//"fileName.jsp","beanName/AttributeName","Object/List/Container"
	}
	

}
