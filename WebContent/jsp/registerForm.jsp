<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/jsp/Template_Includes.jsp" %>
<%@page import="com.ase.web.util.*" %>	

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

    <!-- Product CSS -->
    <link href="css/product.css" rel="stylesheet">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif] 

    -->

	<script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

     <script type="text/javascript">

		function  doProductList(id){
		    //document.forms[0].cmd.value="load";	   
		    document.forms[0].action="<%=request.getContextPath()%>/productList.html?productTypeId="+id;
		    document.forms[0].submit();
		}	
	</script>


</head>
<body>

    <!-- Navigation -->
    <%
    Object obj5 = session.getAttribute(Constant.SS_USEROBJ);
	if (obj5 == null) {
    	%>
    	 <%@ include file="/jsp/Template_UnloginNavigation.jsp" %>
    	<%
    }else{
    	%>
    	<%@ include file="/jsp/Template_LoginNavigation.jsp" %>
    	<%
    }
    %>

<!-- Page Content -->
<form:form method="post" action="loginAuth.html" id="frm" commandName="commandRegForm" >
    
<%--###################################3--%>
 <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">ยืนยันการสั่งซื้อรายการสินค้า</h4>
                </div>
            
                <div class="modal-body">
                    <p>ยืนยันการสั่งซื้อสินค้า.</p>
                    <p>คุณต้องการทำรายการต่อหรือไม่?</p>
                    <%-- <p class="debug-url"></p>--%>
                </div>

                 <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-success btn-ok" id="btn-submit">&nbsp;&nbsp;Yes&nbsp;&nbsp;</a>
                </div>
            </div>
        </div>
    </div>
<%--###################################3--%>
        
	

<div class="container">
    <div class="row">
        <div class="col-md-12">
       
            <div class="well well-sm">
                
                    <fieldset>
                        <legend class="text-center header alert-info">สมัครสมาชิก</legend>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">&nbsp;ชื่อ Login</span>
                            <div class="col-md-8">
                              <%-- <form:input path="userName" type="text" placeholder="User Name" class="form-control" required autofocus />  ${commandRegForm.userName} --%>                             
                               <input type="text" class="form-control" placeholder="* Require User Name" name="userName" value="${commandRegForm.userName}" required autofocus>

                            </div>
                        </div>
                        <br> <br>
		                
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">&nbsp;Password </span>
                            <div class="col-md-8">
                              <%--<form:input path="userName" type="password" placeholder="Password" class="form-control" required autofocus /> --%> 
			                  <input type="password" class="form-control" placeholder="* Require Password" name="userName" value="${commandRegForm.userName}" required autofocus>
						

                            </div>
                        </div>	
                         <br> 	
                         <br>
                         <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">&nbsp;Re Password </span>
                            <div class="col-md-8">
                              <%--<form:input path="userName" type="password" placeholder="Password" class="form-control" required autofocus /> --%> 
			                  <input type="password" class="form-control" placeholder="* Require Re Password" name="userName" value="${commandRegForm.userName}" required autofocus>
						

                            </div>
                        </div>	                


						<legend class="text-center header">&nbsp;</legend>
						
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">&nbsp;ชื่อ </span>
                            <div class="col-md-8">
                             <%--   <form:input path="userName" type="text" placeholder="First Name" class="form-control" />--%> 
			                 <input type="text" class="form-control" placeholder="* Require First Name" name="userName" value="${commandRegForm.userName}" required autofocus>

                            </div>
                        </div>
                        <br> <br>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">นาสกุล </span>
                            <div class="col-md-8">
                               <%-- <form:input path="userName" type="text" placeholder="Last Name"  class="form-control" />--%> 
			                  <input type="text" class="form-control" placeholder="* Require Last Name" name="userName" value="${commandRegForm.userName}" required autofocus>
                               
                            </div>
                        </div>
						<br> <br>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">E-mail</span>
                            <div class="col-md-8">
                             	<%-- <form:input path="userName" type="text" placeholder="Email Address"  class="form-control" />--%> 
			                  <input type="text" class="form-control" placeholder="* Require Email Address" name="userName" value="${commandRegForm.userName}" required autofocus>
                            	
                            </div>
                        </div>
						<br> <br>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">เบอร์โทร</span>
                            <div class="col-md-8">
                                <%--  <form:input path="userName" type="text" placeholder="Mobile"  class="form-control" />--%> 
			                   <input type="text" class="form-control" placeholder="* Require Mobile" name="userName" value="${commandRegForm.userName}" required autofocus>

                            </div>
                        </div>
						<br> <br>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center">ที่อยู่ </span>
                            <div class="col-md-8">
      
                                <%--  <form:textarea path="userName" class="form-control"  placeholder="Enter your Address." rows="7"  cols="35" /> --%> 
                                <textarea name="userName"  class="form-control"  placeholder="* Require Enter your Address." rows="7"  cols="35"  required autofocus ></textarea>
                            </div>
                        </div>

                <div class="form-group">
                    <div class="col-sm-9 col-sm-offset-3">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" required autofocus>ยอมรับเงื่อนไข <a href="#">terms</a>
                            </label>
                        </div>
                    </div>
                </div> 
                        <div class="form-group">
                        <span class="col-md-1 col-md-offset-2 text-center">&nbsp;</span>
                            <div class="col-md-12 text-center">
                          <%-- <button type="button" class="btn btn-primary btn-lg" data-href="orderConfirm.html" data-toggle="modal" data-target="#confirm-delete">ทำรายการต่อ</button> 
                            --%>
                             <button class="btn btn-lg btn-primary btn-lg " type="submit">Submit</button>
                             <button class="btn btn-lg btn-primary btn-lg " type="reset">Reset</button>
                            </div>
                             <%--
		                             <script>
								        $('#confirm-delete').on('show.bs.modal', function(e) {
								            //$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
								           $("#btn-submit").click(function () { 
								        	   //alert("Submit Form");
								        	   $('#frm').attr('action', "orderConfirm.html").submit(); //orderConfirm.html
								        	});
								            //$('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
								        });
							 --%>
                        </div>
                    </fieldset>
         
            </div>
        </div>
    </div>
</div>


          
<!-- ############################################  -->     

    </form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
