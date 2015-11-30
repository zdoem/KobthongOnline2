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

    <!-- Custom CSS -->
    <link href="css/product.css" rel="stylesheet">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif] -->
    
     <script type="text/javascript">
		function  doAdd2Cart(){
		    //document.forms[0].cmd.value="load";	   
		    document.forms[0].action="<%=request.getContextPath()%>/orderProduct.html";
		    document.forms[0].submit();
		}	
		
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
    <form:form method="post" action=""  commandName="productForm">
	<form:hidden path="productId" />
	<form:hidden path="productName" />
	<form:hidden path="productSize" />
	<form:hidden path="productDesc" />
	<form:hidden path="productImageName" />
	<form:hidden path="productQuantity" />
	<form:hidden path="productColor" />
	<form:hidden path="productPrice" />
	<form:hidden path="productSale" />
	<form:hidden path="productLastUpdate" />
	<form:hidden path="adminId" />
	<form:hidden path="typeProductId" />
	<form:hidden path="typeProductName" />
	<form:hidden path="cmd" />

    <div class="container">

        <div class="row">
            <div class="col-md-3">
                <p class="lead">ประเภทสินค้า /${productTypeObj.productTypeName}</p>
                <div class="list-group">
 
                <c:forEach items="${productTypeList}" var="item">
                     <a href="javascript:doProductList('${item.productTypeId}')" class="list-group-item">${item.productTypeName}</a>
                </c:forEach>

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
                </div>
                
               <div class="row carousel-holder">

			<div class="container-fluid">
			    <div class="content-wrapper">	
					<div class="item-container">	
						<div class="container">	
							<div class="col-lg-6">
								<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                                    <ol class="carousel-indicators">
                                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                        <li data-target="#carousel-example-generic" data-slide-to="1" class=""></li>
                                        <li data-target="#carousel-example-generic" data-slide-to="2" class=""></li>
                                    </ol>
                                    <div class="carousel-inner">
                                        <c:forEach var="ImageData" items="${ImageDataList}" varStatus="status">
                                            <c:if test="${status.first}">
                                                <div class="item active" align="center">
                                                    <img class="slide-image" src="data:image/jpeg;base64,${ImageData}" alt="">
                                                </div>
                                            </c:if>
                                            <c:if test="${!status.first}">
                                                <div class="item" align="center">
                                                    <img class="slide-image" src="data:image/jpeg;base64,${ImageData}" alt="">
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                        <span class="glyphicon glyphicon-chevron-left"></span>
                                    </a>
                                    <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                        <span class="glyphicon glyphicon-chevron-right"></span>
                                    </a>
                                </div>
							</div>
								
							<div class="col-md-7">
								<div class="product-title">${productForm.productName}</div>
								<div class="product-desc">รหัสสินค้า :  ${productForm.productId}</div>
								<div class="product-desc">ขนาด : ${productForm.productSize}</div>
								<div class="product-desc">สี : ${productForm.productColor}</div>
								<!-- 
									<div class="product-desc">The Corsair Gaming Series GS600 is the ideal price/performance choice for mid-spec gaming PC</div>
								In Stock
								 -->
								<hr>
								<div class="product-price">
									<c:if test="${productForm.productSale>0}">
										<p>ราคาปกติ :		
										<strike> ${productForm.productPrice} บาท</strike>
										ราคาโปรโมชั่น : ${productForm.productSale} บาท ต่อ ${productForm.productUnitType} 
										</p>
									</c:if>
									<c:if test="${productForm.productSale==0}">
										<p>ราคาปกติ :		
										 ${productForm.productPrice} บาท ต่อ ${productForm.productUnitType} 
										</p>
									</c:if>
								</div>
									<c:if test="${productForm.productQuantity>0}">		
										<div class="product-stock">มีสินค้าพร้อมจำหน่าย</div>
									</c:if>
								<hr>
								<c:if test="${productForm.productQuantity>0}">
									<div class="btn-group cart">
										<button type="button" class="btn btn-success" onclick="javascript:doAdd2Cart();">+เพิ่มลงในตะกร้า</button>
									</div>
								</c:if>
								<%--
								<div class="btn-group wishlist">
									<button type="button" class="btn btn-danger">+Add to wishlist 
									</button>
								</div>
								 --%>

							</div>
						</div> 
					</div>
					<div class="container-fluid">		
						<div class="col-md-12 product-info">
								<ul id="myTab" class="nav nav-tabs nav_tabs">
									
									<li class="active"><a href="#service-one" data-toggle="tab">รายละเอียด</a></li>
									<!--  
										<li><a href="#service-two" data-toggle="tab">PRODUCT INFO</a></li>
										<li><a href="#service-three" data-toggle="tab">REVIEWS</a></li>
									-->
								</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade" id="service-one">
										<section class="container product-info">	
										</section>												  
								</div>
								<div class="tab-pane fade in active" id="service-two">									
									<section class="container">
									${productForm.productDesc} 
									</section>
									
								</div>
								<div class="tab-pane fade" id="service-three">
									<section class="container">
									xxxxxxxxxxxxx
									</section>					
								</div>
							</div>
							<hr>
						</div>
					</div>
				</div>



                </div>

            </div>

        </div>

    </div>
    </div>
    </form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
