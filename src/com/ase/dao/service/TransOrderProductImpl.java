package com.ase.dao.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.ase.model.domain.ProductForm;
import com.ase.web.util.Constant;

public class TransOrderProductImpl implements TransOrderProduct {
	private static org.apache.log4j.Logger Log = Logger.getLogger(MasterProductTypeImpl.class);
	static String sysName = "KobthongOnline";
	static String clazzName = "TransOrderProductImpl";
	
	@Override
	public int InsertTransOrder(Connection conn, List arrList, String INVOID_ID,int customerId,int addressId,int adminId){
		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt = null;
		int i = 1;
		//***********
		try{	
			 ProductForm objForm = null;	
			 Double sumTotal = 0.0d;
			 Double grandTotal = 0.0d;
			 Double pricePostpay = 0.0d;
			 int quantity = 0;
			 if(arrList!=null && arrList.size()>0){
				System.out.println("==>arrList :"+arrList.size());
				ProductForm  prod = null;
			    i = 0;
			    
				Iterator it = arrList.iterator();
			    while(it.hasNext()){
			    	objForm =(ProductForm)it.next(); 	
			    	sumTotal += objForm.getProductSale()*objForm.getProductItems();
			    	pricePostpay = (sumTotal*Constant.FIX_PAY_TAX)+Constant.FIX_PAY_COST;
			    	grandTotal  = pricePostpay+sumTotal;
			    	
			    	quantity +=objForm.getProductItems();
			        i++;
			      }//#While Loop
			  }//#arrList					

			// TODO 	
			sql.delete(0, sql.length());
			sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".order (order_id   ,")//1
				.append(" order_status      ,")//2
				//.append(" order_tracking    ,")//3
				.append(" order_sum_price   ,")//4
				.append(" order_sum_item    ,")//5
				.append(" order_create_date ,")//6
				.append(" order_last_update ,")//7
				.append(" customer_id       ,")//8
				.append(" address_id        ,")//10
				.append(" admin_id        	) ")//9
				.append(" VALUES (?	, ?	, ?	, ?	, NOW()	, NOW()	, ?	, ?	, ?  ) "); 		
								//1   2   3   4   5    6       7      8   9   10
			    i = 1;
			    System.out.println("SQL Insert ORDER :"+sql.toString());
				pstmt = conn.prepareStatement(sql.toString()); 
	   		  	pstmt.setString(i++, INVOID_ID);//order_id
	   		  	pstmt.setString(i++, Constant.F_STATUS_CONFIRM);//order_status= C
	   		  	//pstmt.setString(i++, obj.getIProject());//order_tracking
	   		  	pstmt.setDouble(i++, grandTotal);//order_sum_price
	   		  	pstmt.setInt(i++, quantity);//order_sum_item
	   		    //order_create_date
	   		    //order_last_update
	   		  	pstmt.setInt(i++,customerId);//customer_id
	   		  	pstmt.setInt(i++,addressId);//address_id
	   		  	pstmt.setInt(i++,adminId);//admin_id	   		  	
	   		  	int countRow = pstmt.executeUpdate();
	   		  	
	   			if(countRow == 0){
	   		  		//Fail
	   		  		return -1;
	   		  	}	
	   		  	/********************************/	   		  	
	   			if(arrList!=null && arrList.size()>0){
	   				int seq = 1;
	   				countRow = 0;
	   				sql.delete(0, sql.length());
					sql.append(" INSERT INTO "+Constant.INSTANT_DB_NAME+".order_item (order_id  ,")
					   .append(" order_seq ,")
					   .append(" order_item_quantity ,")
					   .append(" product_id          ,")
					   .append(" order_item_price    ) ")
					   .append("  VALUES (?, ? , ?, ?, ?) "); 	

					 System.out.println("SQL Insert ORDER_ITEM :"+sql.toString());
					  Iterator it = arrList.iterator();
					  while(it.hasNext()){
						    objForm =(ProductForm)it.next(); 
						    i=1;
							pstmt = conn.prepareStatement(sql.toString()); 
							pstmt.setString(i++,INVOID_ID);
							pstmt.setInt(i++,seq);
							pstmt.setInt(i++,objForm.getProductItems());
							pstmt.setString(i++,objForm.getProductId());
							pstmt.setDouble(i++,objForm.getProductSale());
							seq++;							  
							//-->>System.out.println("--->"+sql.toString());
							countRow += pstmt.executeUpdate(); 
					  }					    
    	   		  }
	   		  	/********************************/			
	   		  	return countRow;			
		}catch(Exception e){
			//e.fillInStackTrace();
			System.out.println(clazzName+":InsertTransOrder :"+e.toString());
			return -1;//#End
		}finally{
			try {
				if(pstmt!=null)
					pstmt.close();
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
}
