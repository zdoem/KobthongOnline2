<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="/jsp/Template_Includes.jsp" %>
<%//@ include file="/jsp/template_header.jsp" %>
<script language="javascript">
</script>
</head>
<body>
 <div id="main">
<img src="images/pets.png" align="right" style="position:relative;right:30px;">
<h2>Welcome 'test' System</h2>
<P><b>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; 'test'  xxxx <br>


</b></P>

<ul>
   <li><a href="<c:url value="/test.html"/>">Test Blank Spring MVC 3.0</a></li>
   <br>
  <li><a href="<c:url value="/homeTest.html"/>">หน้า Home Test</a></li>
  <li><a href="<c:url value="/home.html"/>">หน้า Home ครับ</a></li>
  <hr>
  <li><a href="<c:url value="/login.html"/>">Login</a></li>
  <li><a href="<c:url value="/Logout.html"/>">Logout</a></li>
 <li><a href="<c:url value="/orderStatusList.html"/>">รายการสั่งซื้อ</a></li>

  <hr>
    <li><a href="<c:url value="/TypeProductAddForm.dog"/>"><strike>ข้อมูลพื้นฐาน  ประเภทสินค้า</strike></a></li>
  
  
   <hr>
   <li><a href="<c:url value="/CustomerAddForm.dog"/>"><strike>ข้อมูลพื้นฐาน  เพิ่มชื่อลูกค้า</strike></a></li>
  <li><a href="<c:url value="/CustomerList.dog"/>"><strike>รายการ รายการชื่อลูกค้า</strike></a></li>
   
   <hr>
   <li><a href="<c:url value="/UsersAddForm.dog"/>"><strike>ข้อมูลพื้นฐาน  เพิ่ม User</strike></a></li>
  <li><a href="<c:url value="/UsersList.dog"/>"><strike>รายการ รายการ User</strike></a></li>
  
   <hr>
   <li><a href="<c:url value="/SupplierAddForm.dog"/>"><strike>ข้อมูลพื้นฐาน  เพิ่ม  Supplier</strike></a></li>
   <li><a href="<c:url value="/SupplierList.dog"/>"><strike>รายการ รายการ Supplier</strike></a></li>
  
  <hr>
   <li><a href="<c:url value="/ProductAddForm.dog"/>"><strike>ข้อมูลพื้นฐาน  เพิ่ม  รายการสินค้า</strike></a></li>
  <li><a href="<c:url value="/ProductList.dog"/>"><strike>รายการ รายการ สิ้นค้า</strike></a></li>
  
  <hr>
   <li><a href=" http://localhost:8080/merchant2/jsp/welcome2.jsp"><strike>POST / Sale / Report</strike></a></li>
 
  <br></br>
  <br></br>

  <br></br>
</ul>
<br style="font-size:5pt">
<br style="font-size:5pt">
<br style="font-size:5pt">

<p>&nbsp;</p>

<%//@ include file="/jsp/template_footer.jsp" %>
