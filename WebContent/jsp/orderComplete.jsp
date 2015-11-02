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
    <form:form method="post" action=""  >
    
    <div class="container">

        <div class="row">
            <div class="col-md-3">
                <p class="lead">ประเภทสินค้า /รายการสั่งซื้อสินค้า</p>
                <div class="list-group">
 
                <c:forEach items="${productTypeList}" var="item">
                     <a href="javascript:doProductList('${item.productTypeId}')" class="list-group-item">${item.productTypeName}</a>
                </c:forEach>

                </div>
            </div>


         <!--  ###########################################
         	 <div class="account-wall ">&nbsp;</div>
          -->              
             <div class="col-md-7">
					
				       <div class="account-wall ">
				        <img class="profile-img" src="images/success.png"  width="100px"  height="100px"  alt="" >
				       <h4 class="alert alert-success">
				       ทำรายการสั่งซื้อเรียบร้อยแล้ว..</h4>
				       </div>
				       <hr>
				       <div class="account-wall ">
				       <h5 class="alert alert-info" ><label align="center"> การชำระเงิน </label>
				       <br>
				       <hr>
				     ท่านสามารถเลือกชำระค่าบริการได้ดังต่อไปนี้
					<br>1.โอนเงินผ่านทางเคาน์เตอร์ธนาคาร
    				 กรุณาโอนเงินเข้าบัญชีดังต่อไปนี้ หลังจากโอนเงินเรียบร้อยแล้ว กรุณาแฟกซ์หลักฐานการโอนเงิน มาที่หมายเลข 0-2555-5555 
					หรือ สแกนหลักฐานการโอนเงิน แล้วส่งมาที่ e-mail : kobthong55@gmail.com จากนั้นทางร้านจะดำเนินการจัดส่งงสินค้าไปทางไปรษณีย์ท่านสามารถ Tracking เลขที่พัสดุได้ในหน้า
					ตรวจสอบสถานะการจัดส่งสินค้า
					
					<br><li>ธนาคาร  : กรุงไทย 
					<br><li>สาขา : มหาวิทยาลัยเทคโนโลยีพระจอมเกล้าพระนครเหนือ
					<br><li>ชื่อบัญชี:ร้านกอบทองจำกัดดอทคอม 
					<br><li>เลขที่บัญชี :999-9-99999-9 
					<br><li>ประเภท :ออมทรัพย์ 

					<br>2.โอนเงินผ่านเครื่อง ATM ของธนาคาร
					     ท่านสามารถทำรายการโอนเงินผ่านเครื่อง ATM โดยระบุเลขที่บัญชี และ จำนวนเงินที่ต้องการโอนให้ถูกต้อง โดยปฏิบัติตามขั้นตอน/ 
					คำแนะนำจากเครื่อง ATM ของแต่ละธนาคาร
				     </h5>
				     </div>
				     <h4 class="alert alert-danger" >* ห้าม! โอนเงินผ่านเครื่อง ATM ที่กระดาษบันทึกรายการหมด เพราะท่านจะไม่มีหลักฐานในการแจ้งยืนยันการโอนเงิน 
 โปรดเก็บใอนเงินไว้เป็นหลักฐานการชำระเงิน  กรุณาอย่าทำหายเป็นอันขาด
				     </h4>

            </div>    
            <!-- ############################################  -->     


    </div>
    </div>
 
    </form:form>
    <!-- /.container -->
<%@ include file="/jsp/Template_Footer.jsp" %>

</body>

</html>
