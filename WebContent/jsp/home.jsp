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
		    //alert("Test "+id);
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
                <p class="lead">ร้านค้าพัสดุภัณฑ์  /หน้าแรก</p>
                <div class="list-group">
 
                <c:forEach items="${productTypeList}" var="item">
                      <a href="javascript:doProductList('${item.productTypeId}')" class="list-group-item">${item.productTypeName}</a>
                </c:forEach>

                </div>
            </div>

            <div class="col-md-9">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img class="slide-image" src="images/Promotion.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="images/Banner.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="images/ListName.jpg" alt="">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>

                </div>

                <div class="row">

                <c:forEach items="${productList}" var="productItems" varStatus="state">
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <img src="data:image/jpeg;base64,${imageDatas[state.index]}" height="150" alt="${productItems.productName}">
                            <div class="caption">
                                <h4><a href="productDesc.html?productId=${productItems.productId}" >${productItems.productName}</a></h4>
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
			
                  <!-- 
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <h4><a href="#">Like this template?</a>
                        </h4>
                        <p>If you like this template, then check out <a target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">this tutorial</a> on how to build a working review system for your online store!</p>
                        <a class="btn btn-primary" target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">View Tutorial</a>
                    </div>
					-->
                </div>

            </div>

        </div>

    </div>
    </form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
