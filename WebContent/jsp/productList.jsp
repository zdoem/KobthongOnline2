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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif] -->
    
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
    Object obj = session.getAttribute(Constant.SS_USEROBJ);
	if (obj == null) {
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
    <form:form method="post" action="" >
    <div class="container">

        <div class="row">

            <div class="col-md-3">
                <p class="lead">ประเภทสินค้า/${commandType.productTypeName}</p>
                <div class="list-group">
 
                <c:forEach items="${productTypeList}" var="item">
                     <a href="javascript:doProductList('${item.productTypeId}')" class="list-group-item">${item.productTypeName}</a>
                </c:forEach>
                <!--  
                    <a href="#" class="list-group-item">Category 1</a>
                    <a href="#" class="list-group-item">Category 2</a>
                    <a href="#" class="list-group-item">Category 3</a>
                    <div class="msg"><FONT color="#7FFF00">${msg}</FONT></div>
                 -->
                </div>
            </div>

            <div class="col-md-9">
                <div class="row">
				
				<c:if test="${not empty msg}">					
						<div class="col-sm-6 col-md-4 col-md-offset-4">
				            <h2 class="text-center login-title"><FONT color="#00CC00">ไม่พบรายการสินค้า</FONT></h2>
				            <div class="account-wall">
				                <img class="profile-img" src="images/pets.png"    alt="" >
				           </div>
			          </div>
				</c:if>
					
                <c:forEach items="${productListByType}" var="productItems">
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <img src="http://placehold.it/320x150" alt="${productItems.productName}">
                            <div class="caption">
                                <h5><a href="productDesc.html?productId=${productItems.productId}" >${productItems.productName}</a></h5>
                                <p>                              
                                <div align="left" style="border:1px solid red" ><strike>ราคา : ${productItems.productPrice}</strike>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ราคาโปรโมชั่น :${productItems.productSale}</div>
                                </p>
                                <p>${productItems.productDesc} ... 
                               <%--
                               <a target="_blank" href="http://www.bootsnipp.com">Bootsnipp - http://bootsnipp.com</a>.
                                --%>
                                </p>
                            </div>
                             <%--  
                             <div class="ratings">
                                <p class="pull-right">15 reviews</p>
                                <p>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                </p>
                            </div>                            
                             --%>

                            
                        </div>
                    </div>
                </c:forEach>

                </div>

            </div>

        </div>

    </div>
    </form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
