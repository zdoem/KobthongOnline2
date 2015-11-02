
 <%@page pageEncoding="UTF-8"%>
<%
String msgStep = request.getAttribute("msgStep")==null?"1":request.getAttribute("msgStep").toString();
System.out.println("=========== msgStep :"+msgStep);
%>
 
<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
          <% if("1".equals(msgStep)){ %>
             <button type="button" class="btn btn-primary btn-circle"  >1</button>
            <%}else{ %>
              <button type="button" class="btn btn-default btn-circle"  disabled="disabled" >1</button>
            <%} %>
            <p>ตะกร้าสินค้า</p>
        </div>
        <div class="stepwizard-step">
	      <% if("2".equals(msgStep)){ %>
	            <button type="button" class="btn btn-primary btn-circle" >2</button>
	         <%}else{ %>
	            <button type="button" class="btn btn-default btn-circle" disabled="disabled">2</button>
	         <%} %>
            <p>ยืนยันที่จัดส่ง</p>
        </div>
        <div class="stepwizard-step">
	       <% if("3".equals(msgStep)){ %>
	            <button type="button" class="btn btn-primary btn-circle" >3</button>
	        <%}else { %>  
	         <button type="button" class="btn btn-default btn-circle" disabled="disabled">3</button>
	        <%} %>  
            <p>ทำรายการเรียบร้อย</p>
        </div> 
    </div>
</div>
