<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/jsp/Template_Includes.jsp" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"> 
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ร้านค้าพัสดุภัณฑ์ จัดจำหน่ายพลาสติก,ขวดบรรจุภัณฑ์</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/shop-homepage.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif] -->
    

</head>

<body>

    <!-- Navigation -->
	<%@ include file="/jsp/Template_UnloginNavigation.jsp" %>

    <!-- Page Content -->

    <form:form method="post" action="loginAuth.html" commandName="loginForm">
	<div class="container">
	    <div class="row">
	        <div class="col-sm-6 col-md-4 col-md-offset-4">
	            <h1 class="text-center login-title">กรุณา Login เข้าสู่ระบบ</h1>
	            <div class="account-wall">
	                <img class="profile-img" src="images/pets.png"
	                    alt="" >

				<c:if test="${not empty error}">
					<div class="error"><FONT color="red">${error}</FONT></div>
				</c:if>
				
				<c:if test="${not empty msg}">
					<div class="msg"><FONT color="red">${msg}</FONT></div>
				</c:if>
		
	                <form class="form-signin">
	               
	               <input type="text" class="form-control" placeholder="Email" name="userName" value="${command.userName}" required autofocus>
	               <input type="password" class="form-control" placeholder="Password" name="userPassword"  value="${command.userPassword}" required>
	                      
	                <%-- 
	                <form:input path="userName" type="text"  class="form-control" placeholder="Email"  />
					<form:input path="userPassword" type="password" class="form-control" placeholder="Password"  />
					
                
	                <input type="text" class="form-control" placeholder="Email" path="userName" required autofocus>
	                <input type="password" class="form-control" placeholder="Password" path="userPassword"  required>
	                 <p class="submit"><input type="button" value=" ADD " onclick="javascript:onSAVE();"/>&nbsp;&nbsp;<input type="reset" value=" Reset "/></p>
	                --%>
	                
	                <button class="btn btn-lg btn-primary btn-block" type="submit">
	                    Sign in</button>
	                <label class="checkbox pull-left">
	                    <input type="checkbox" value="remember-me">
	                                                     ให้จำฉันในระบบต่อไป
	                </label>
	                <%-- 
	                <a href="#" class="pull-right need-help">Need help? </a>
	                --%>
	                <span class="clearfix"></span>
	                </form>
	            </div>
	            <a href="#" class="text-center new-account">สมัครใช้งาน </a>
	        </div>
	    </div>
	    
	</div>
	</form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
