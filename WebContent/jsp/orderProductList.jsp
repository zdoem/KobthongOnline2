<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/jsp/Template_Includes.jsp"%>
<%@ include file="/jsp/VerifyAuthorize.jsp"%>
<%@page import="com.ase.web.util.*"%>

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

    <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
    <script data-require="bootstrap@*" data-semver="3.1.1" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="script.js"></script>
    -->

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/validator.js"></script>

<script type="text/javascript">

		function  doOrderDelete(id){
		    //document.forms[0].cmd.value="load";	   
		    document.forms[0].action="<%=request.getContextPath()%>/orderProductDelete.html?productId="+id;
		    document.forms[0].submit();
		}
		function  doCheckOut(){
		    //document.forms[0].cmd.value="load";	   
		    document.forms[0].action="<%=request.getContextPath()%>/orderCheckout.html";
		    document.forms[0].submit();
		}
		
		function  doProductList(id){
		    //document.forms[0].cmd.value="load";	   
		    document.forms[0].action="<%=request.getContextPath()%>/productList.html?productTypeId="+id;
		    document.forms[0].submit();
		}
		function doRefresh(id,max){
			var qty =  parseInt(document.getElementById(id).value);
			if(qty > parseInt(max)){
				document.getElementById(id).value = parseInt(max);
			}
			document.forms[0].action="<%=request.getContextPath()%>/orderRefresh.html";
		    document.forms[0].submit();
		}
	</script>

</head>
<body>

	<%--
  <a href="#" data-href="/delete.php?id=23" data-toggle="modal" data-target="#confirm-delete">Delete record #23</a><br>
   <button class="btn btn-default" data-href="/delete.php?id=54" data-toggle="modal" data-target="#confirm-delete">
        Delete record #54</button>
--%>


	<!-- set up the modal to start hidden and fade in and out -->
	<!-- Page Content -->
	<form:form method="post" action="" id="frm">

		<!-- Navigation -->
		<%
			Object obj5 = session.getAttribute(Constant.SS_USEROBJ);
				if (obj5 == null) {
		%>
		<%@ include file="/jsp/Template_UnloginNavigation.jsp"%>
		<%
			} else {
		%>
		<%@ include file="/jsp/Template_LoginNavigation.jsp"%>
		<%
			}
		%>
		<div class="container">

			<div class="row">
				<div class="col-md-3">
					<p class="lead">ประเภทสินค้า /รายการสั่งซื้อสินค้า</p>
					<div class="list-group">

						<c:forEach items="${productTypeList}" var="item">
							<a href="javascript:doProductList('${item.productTypeId}')"
								class="list-group-item">${item.productTypeName}</a>
						</c:forEach>

					</div>
				</div>

				<div class="col-md-9">
					<div class="row">
						<c:if test="${not empty msg}">
							<div class="col-sm-6 col-md-4 col-md-offset-4">
								<h2 class="text-center login-title">
									<FONT color="#00CC00">ไม่พบรายการสินค้า</FONT>
								</h2>
								<div class="account-wall">
									<img class="profile-img" src="images/pets.png" alt="">
								</div>
							</div>
						</c:if>
					</div>

					<div class="row carousel-holder">

						<%-- ############################### --%>
						<div class="container">

							<%-- ############################### --%>
							<%@ include file="/jsp/Template_ProcessStep.jsp"%>
							<%-- ############################### --%>

							<div class="row">
								<div class="col-sm-12 col-md-10 col-md-offset-1">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>รายการสินค้า</th>
												<th>จำนวน</th>
												<th class="text-center ">ราคา/หน่วย</th>
												<th class="text-center">รวม</th>
												<th> </th>
											</tr>
										</thead>
										<tbody>

											<c:if test="${SS_ARR_ORDERLIST != null}">

												<c:set var="sumTotal" value="0.0" />
												<c:set var="grandTotal" value="0.0" />
												<c:set var="pricePostpay" value="0.0" />

												<c:forEach items="${SS_ARR_ORDERLIST}" var="productItems"
													varStatus="loop">

													<tr>
														<td class="col-md-6">
															<div class="media">
																<%--
             					<a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>		                        
		                         --%>
																<div class="media-body">
																	<h4 class="media-heading">${productItems.productName}</h4>
																	<h5 class="media-heading">
																		รหัสสินค้า <a
																			href="productDesc.html?productId=${productItems.productId}">${productItems.productId}</a>
																	</h5>
																	<span>สถานะ: </span><span class="text-success"><strong>มีสินค้าพร้อมขาย</strong></span>
																</div>
															</div>
														</td>


														<td class="col-sm-1 col-md-2"><input
															id = "quantity${loop.index}"
															name="quantity${loop.index}"
															onchange="doRefresh('quantity${loop.index}','${productItems.productQuantity}')"
															value="${productItems.productItems}" type="number"
															class="form-control" max="${productItems.productQuantity}"/></td>
														<td class="col-sm-1 col-md-1 text-center">
															<c:if test="${productItems.productSale>0}">
																<strong>&nbsp;${productItems.productSale}&nbsp;</strong>
															</c:if> <c:if test="${productItems.productSale==0}">
																<strong>&nbsp;${productItems.productPrice}&nbsp;</strong>
															</c:if>
														</td>

														<c:if test="${(productItems.productSale > 0) &&  (productItems.productItems > 0)}">
															<td class="col-sm-1 col-md-1 text-center">
															<strong><c:out value="${productItems.productSale*productItems.productItems}" /></strong>
															</td>
															<c:set var="sumTotal" value="${sumTotal+productItems.productSale*productItems.productItems}" />
															<c:set var="pricePostpay" value="${(sumTotal*0.07)+25}" />
															<c:set var="grandTotal" value="${pricePostpay+sumTotal}" />
														</c:if>
														
														<c:if test="${(productItems.productSale == 0) &&  (productItems.productItems > 0)}">
															<td class="col-sm-1 col-md-1 text-center">
															<strong><c:out value="${productItems.productPrice*productItems.productItems}" /></strong>
															</td>
															<c:set var="sumTotal" value="${sumTotal+productItems.productPrice*productItems.productItems}" />
															<c:set var="pricePostpay" value="${(sumTotal*0.07)+25}" />
															<c:set var="grandTotal" value="${pricePostpay+sumTotal}" />
														</c:if>

														<td class="col-sm-1 col-md-1">
															<button type="button" class="btn btn-danger"
																onclick="javascript:doOrderDelete(${productItems.productId});">
															<span class="glyphicon glyphicon-remove"></span> Remove
															</button>
														</td>
													</tr>

												</c:forEach>

												<tr>
													<td> </td>
													<td> </td>
													<td> </td>
													<td><h5>รวม</h5></td>
													<td class="text-right"><h5>
															<strong><c:out value="${sumTotal}"></c:out></strong>
														</h5></td>
												</tr>
												<tr>
													<td> </td>
													<td> </td>
													<td> </td>
													<td><h5>ค่าจัดส่งสินค้า</h5></td>

													<td class="text-right"><h5>
															<strong><fmt:formatNumber minIntegerDigits="2"
																	value="${pricePostpay}" /></strong>
														</h5></td>
												</tr>
												<tr>
													<td> </td>
													<td> </td>
													<td> </td>
													<td><h3>รวมทั้งหมด</h3></td>
													<td class="text-right"><h3>
															<strong>${grandTotal}</strong>
														</h3></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<c:if test="${empty SS_ARR_ORDERLIST}">
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</c:if>
													<c:if test="${not empty SS_ARR_ORDERLIST}">
														<td>
															<!-- Continue Shopping -->
															<button type="button" id="btn-submit"
																class="btn btn-default">
																<span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;&nbsp;ทำรายการต่อ&nbsp;&nbsp;
															</button>
														</td>
														<td>
															<button type="button" class="btn btn-success"
																onclick="javascript:doCheckOut();">
																Checkout <span class="glyphicon glyphicon-play"></span>
															</button>
														</td>
														<script>
								           $("#btn-submit").click(function () { 
								        	   $('#frm').attr('action', "orderChangeAdress.html").submit(); //orderConfirm.html
								        	});
								    </script>
													</c:if>
												</tr>

											</c:if>

										</tbody>
									</table>
								</div>
							</div>

							<!-- ############################################  -->


						</div>

					</div>

				</div>
			</div>
		</div>
	</form:form>
	<!-- /.container -->
	<%@ include file="/jsp/Template_Footer.jsp"%>

</body>

</html>
