<%@page pageEncoding="UTF-8"%>
<%@page import="com.ase.web.util.*" %>		    
<% 
          // session = request.getSession(false); 
		  if(session == null) {
			   System.out.println("******* (session == null) ********");
				 //** Redirect user to login page if
				  // there's no session.*//*
				  response.sendRedirect(request.getContextPath()+"/login.html");
				 return;
		   }
		  
		  Object obj = session.getAttribute(Constant.SS_USEROBJ);
		  if (obj == null) {
		    	System.out.println("----->UserLogin is null");
		    	System.out.println("******* (obj == null)  ********");
		        //** Redirect user to login page if
		        // there's no session.*//*
		        response.sendRedirect(request.getContextPath()+"/login.html");
		        return;
		   }
		  
		  Object obj2 = session.getAttribute(Constant.SS_CUSTOMER_PROFILE);
		  if (obj2 == null) {
		    	System.out.println("----->SS_CUSTOMER_PROFILE is null");
		    	System.out.println("******* (obj2 == null)  ********");
		        //** Redirect user to login page if
		        // there's no session.*//*
		        response.sendRedirect(request.getContextPath()+"/login.html");
		        return;
		   }
%>
 