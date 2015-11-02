package com.ase.web.controller;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ase.dao.service.Common;
import com.ase.dao.service.GenerateAutoID;
import com.ase.dao.service.MasterProductType;
import com.ase.dao.service.MasterProductTypeImpl;
import com.ase.dao.service.TransOrderProduct;
import com.ase.dao.service.TransOrderProductImpl;
import com.ase.dao.service.UserService;
import com.ase.dao.service.UserServiceImpl;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;
import com.ase.model.domain.ChangeAddressCustForm;
import com.ase.model.domain.LoginForm;
import com.ase.model.domain.ProductForm;
import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;


@Controller
@SessionAttributes(Constant.SS_ARR_ORDERLIST)
public class TransOrderProductController {
	static String  clazzName =  "TransOrderProductController";
	static{
		//initial  First Request  configure  datasouce only
		//Use  connection db server type  Connection pool
		Common.setConfigForConnectionPool("", Constant.DataSourceName);
	}
	
	//Show all parameter All by pass Request query.
	private static void printOutParam(HttpServletRequest request){
		String paramNames = "";
		System.out.println("---------[ Parameter List] ------------");
		for(Enumeration e = request.getParameterNames();e.hasMoreElements(); ){
			paramNames = (String)e.nextElement();
			System.out.println(paramNames+" = "+request.getParameter(paramNames));
		}		
		System.out.println("---------- [Parameter List] -----------");		
	}
	
	
	//*********ADD Session Step # 1
	@RequestMapping(value = "/orderProduct") //, method = RequestMethod.GET,POST
	public ModelAndView doOrdderAddAction(@ModelAttribute("prodForm")
			ProductForm prodForm, BindingResult result,HttpServletRequest request)  {
		//productDesc.html?productId=2015102100008
		Connection conn = null;
		String forward = "";
		try{		
				System.out.println("--->Order  orderProduct.html");
				printOutParam(request);
				
				System.out.println("========"+prodForm.getProductId());
				System.out.println("========"+prodForm.getProductName());
				System.out.println("========"+prodForm.getProductQuantity());
				/**************
				* Verify Login or Require Login Only
				/**************/
				 HttpSession session = request.getSession(false); 
				//session check login			   
				if(Utilize.verifyLogin(session)){
				   //case Not login
					forward =  "redirect:/login.html";
					System.out.println("========XXXXXXXXXXXXXXXXXXXXXXXXx Login.html");
					return new ModelAndView(forward);
				}
	
				Object oby = new Object();			    						
				ArrayList arrList = new ArrayList();
			   	try{
			   		oby = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
			     	arrList = (ArrayList)oby; 
			   	}catch(Exception e){
				    System.out.println("<<------------Exception View");
		    	}  					
				//*********************************************		
				
			   	/********** Connection DB *************/
				conn = Common.open();
				Common.defaultTransaction(conn);
				/********** Connection DB *************/
				
				/*********check duplicate  product */
				boolean isDup = true;
				ProductForm objForm = null;			     	
				if(arrList!=null && arrList.size()>0){
					ProductForm  prod = null;
				    int items = 0;
				    int i = 0;
				     		
					System.out.println("Check duplicate process.");
					Iterator it=arrList.iterator();
				    while(it.hasNext()){
				        prod =(ProductForm)it.next();
				    	System.out.println("==>prod :"+prod.getProductId());
				    	System.out.println("===prodForm "+prodForm.getProductId());
				    	if(prod.getProductId().equals(prodForm.getProductId())){
				        	//items = prod.getProductItems()+prodForm.getProductItems();
				    		items = prod.getProductItems()+1;
				        	System.out.println("==>items :"+items+",i="+i);
				        	arrList.remove(i);
				        	
				        	objForm = new ProductForm();
				        	//Set items 
				        	objForm.setProductItems(items);
				        	objForm.setProductId(prodForm.getProductId());
				        	objForm.setProductName(prodForm.getProductName());
				        	objForm.setProductSize(prodForm.getProductSize());
				        	objForm.setProductDesc(prodForm.getProductDesc());
				        	objForm.setProductImageName(prodForm.getProductImageName());
				        	objForm.setProductQuantity(prodForm.getProductQuantity());
				        	objForm.setProductColor(prodForm.getProductColor());
				        	objForm.setProductPrice(prodForm.getProductPrice());
				        	objForm.setProductSale(prodForm.getProductSale());
				        	objForm.setProductLastUpdate(prodForm.getProductLastUpdate());
				        	objForm.setAdminId(prodForm.getAdminId());
				        	objForm.setTypeProductId(prodForm.getTypeProductId());
				        	objForm.setTypeProductName(prodForm.getTypeProductName());

							isDup = false;
					    	break;
				          }//#if duplicate
				          i++;
				       }//#While Loop
				     }//#arrList		     	
				     //*****Not duplicate krup
				    System.out.println("cccccccccccccccccccc.");
				     if(isDup){
				    	 objForm = new ProductForm();
				    	 objForm.setProductItems(1); //from input form items
				    	 objForm.setProductId(prodForm.getProductId());
				    	 objForm.setProductName(prodForm.getProductName());
				    	 objForm.setProductSize(prodForm.getProductSize());
				    	 objForm.setProductDesc(prodForm.getProductDesc());
				    	 objForm.setProductImageName(prodForm.getProductImageName());
				    	 objForm.setProductQuantity(prodForm.getProductQuantity());
				    	 objForm.setProductColor(prodForm.getProductColor());
				    	 objForm.setProductPrice(prodForm.getProductPrice());
				    	 objForm.setProductSale(prodForm.getProductSale());
				    	 objForm.setProductLastUpdate(prodForm.getProductLastUpdate());
				    	 objForm.setAdminId(prodForm.getAdminId());
						 objForm.setTypeProductId(prodForm.getTypeProductId());
						 objForm.setTypeProductName(prodForm.getTypeProductName());				
				     }	
				     System.out.println("rrrrrrrrrrrrrrr.");
					if(arrList == null){
						arrList = new ArrayList();				
					}
					arrList.add(objForm);				
					//session.removeAttribute("arrOrderList");//remove
	       			//session.setAttribute(Constant.SS_ARR_ORDERLIST,arrList);//add	
   
				/*******New Object ****************/
				MasterProductType productTypeService = new MasterProductTypeImpl();
			   	List productTypeList =  productTypeService.listProductType(conn);
			   	
			   	System.out.println("============Order productForm List=====================");
			   	
			   	forward =  "orderProductList"; //orderProductList.html
				ModelAndView model = new ModelAndView(forward);
				model.addObject("productTypeList", productTypeList);
				model.addObject(Constant.SS_ARR_ORDERLIST,arrList);
				model.addObject("msgStep","1");//Step = 1
				//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
				
				Common.close(conn);
				conn = null;
				return model;
			}catch(Exception e){
				//request.setAttribute("args1", e.getMessage());
			    forward = "error100"; //error100.jsp
			    System.out.println(clazzName+":"+e.getMessage());
			    System.out.println(e.toString());
				 try{
					 if(conn!=null){
						Common.close(conn);
					 }
					 conn = null;
				    //Close connection
				 }catch(Exception ex){}
				 return new ModelAndView(forward);
		     }finally{
				 try{
					 if(conn!=null){
						Common.close(conn);
					 }
					 conn = null;
				    //Close connection
				 }catch(Exception ex){}
		     }
		 }
	
	//*********Check Out
	@RequestMapping(value = "/orderCheckout") //, method = RequestMethod.GET,POST
	public ModelAndView doOrdderCheckoutAction(HttpServletRequest request)  {
			//productDesc.html?productId=2015102100008		
			Connection conn = null;
			String forward = "";
			try{		
					System.out.println("===============>Order  orderCheckProduct.html=============");
					printOutParam(request);

					/**************
					* Verify Login or Require Login Only
					/**************/
					 HttpSession session = request.getSession(false); 
					//session check login			   
					if(Utilize.verifyLogin(session)){
					   //case Not login
						forward =  "redirect:/login.html";
						System.out.println("========XXXXXXXXXXXXXXXXXXXXXXXXx Login.html");
						return new ModelAndView(forward);
					}
		
					Object oby = new Object();			    						
					ArrayList arrList = new ArrayList();
				   	try{
				   		oby = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
				     	arrList = (ArrayList)oby; 
				   	}catch(Exception e){
					    System.out.println("<<------------Exception View");
			    	}  					
					//*********************************************		
					
				   	/********** Connection DB *************/
					conn = Common.open();
					Common.defaultTransaction(conn);
					/********** Connection DB *************/
					
					/*********check duplicate  product */
					ArrayList arrNewList = new ArrayList();
					ProductForm objForm = null;			     	
					if(arrList!=null && arrList.size()>0){
						System.out.println("==>arrList :"+arrList.size());
						
						ProductForm  prod = null;
					    int i = 0;
					    String quantity = "";
					    
						System.out.println("=========== process.");
						Iterator it=arrList.iterator();
					    while(it.hasNext()){
						    prod =(ProductForm)it.next(); 
						    quantity = request.getParameter("quantity"+i);    
						        
						    System.out.println("==>prod :"+prod.getProductId());
						    System.out.println("==>quantity :"+quantity);
						    	
						    objForm = new ProductForm();
					        //Set items 
						    //objForm.setProductItems(5);
					        objForm.setProductItems(Integer.parseInt(quantity));
					        objForm.setProductId(prod.getProductId());
					        objForm.setProductName(prod.getProductName());
					        objForm.setProductSize(prod.getProductSize());
					        objForm.setProductDesc(prod.getProductDesc());
					        objForm.setProductImageName(prod.getProductImageName());
					        System.out.println("=========== process1111.");
					        objForm.setProductQuantity(prod.getProductQuantity());
					        objForm.setProductColor(prod.getProductColor());
					        objForm.setProductPrice(prod.getProductPrice());
					        objForm.setProductSale(prod.getProductSale());
					        objForm.setProductLastUpdate(prod.getProductLastUpdate());
					        System.out.println("=========== process222.");
					        objForm.setAdminId(prod.getAdminId());
					        objForm.setTypeProductId(prod.getTypeProductId());
					        objForm.setTypeProductName(prod.getTypeProductName());
					        System.out.println("=========== process3333.");
					        //arrList.remove(i); //remove old
					        System.out.println("=========== process444.");
					        arrNewList.add(objForm);//add new item
					        System.out.println("=========== process555.");
					        i++;
					      }//#While Loop
					    }//#arrList		     	
					    System.out.println("8888888888888888.");

						if(arrNewList == null){							
						    System.out.println("=====New ArrayList.");
							arrNewList = new ArrayList();				
						}
						//arrList.add(objForm);		
						synchronized(session) {
							session.removeAttribute(Constant.SS_ARR_ORDERLIST);//remove
							System.out.println("=========== xxxxxxxxxxxxxxxxxx.");
						}	
		       			//session.setAttribute(Constant.SS_ARR_ORDERLIST,arrNewList);//add	
						System.out.println("=========== process666666.");
					/*******New Object ****************/
					MasterProductType productTypeService = new MasterProductTypeImpl();
				   	List productTypeList =  productTypeService.listProductType(conn);
				   	
				   	System.out.println("============Order productForm List=====================");				   	
				   	forward = "redirect:/home.html"; //orderProductList.html
					ModelAndView model = new ModelAndView(forward);
					model.addObject(Constant.SS_ARR_ORDERLIST,arrNewList);

					//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
					
					Common.close(conn);
					conn = null;
					return model;
				}catch(Exception e){
				    forward = "error100"; //error100.jsp
				    System.out.println(clazzName+":"+e.getMessage());
				    System.out.println(e.toString());
					 try{
						 if(conn!=null){
							Common.close(conn);
						 }
						 conn = null;
					    //Close connection
					 }catch(Exception ex){}
					 return new ModelAndView(forward);
			     }finally{
					 try{
						 if(conn!=null){
							Common.close(conn);
						 }
						 conn = null;
					    //Close connection
					 }catch(Exception ex){}
			     }
			 }
	
	//********* orderProductList  :: Step#1
	@RequestMapping(value = "/orderProductList") //, method = RequestMethod.GET,POST
	public ModelAndView doOrdderListAction(HttpServletRequest request)  {
		//productDesc.html?productId=2015102100008
		
		Connection conn = null;
		String forward = "";
		try{		
				System.out.println("--->Order  orderProductList.html");
				printOutParam(request);

				/**************
				* Verify Login or Require Login Only
				/**************/
				 HttpSession session = request.getSession(false); 
				//session check login			   
				if(Utilize.verifyLogin(session)){
				   //case Not login
					forward =  "redirect:/login.html";
					System.out.println("========XXXXXXXXXXXXXXXXXXXXXXXXx Login.html");
					return new ModelAndView(forward);
				}
				Object oby = new Object();			    						
				ArrayList arrList = new ArrayList();
			   	try{
			   		oby = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
			     	arrList = (ArrayList)oby; 
			   	}catch(Exception e){
				    System.out.println("<<------------Exception View");
		    	}  					
				//*********************************************		
			   	if(arrList!=null && arrList.size()>0){
			   		System.out.println("============XXXXXXXXXXXXXXXXXXXXXXXXXXXXXx====================");
			   	}else{
			   		arrList= new ArrayList();
			   		System.out.println("============TTTTTTTTTTTTTTTTTTTTTTT====================");
			   	}
			   	/********** Connection DB *************/
				conn = Common.open();
				Common.defaultTransaction(conn);
				/********** Connection DB *************/
				
				/*******New Object ****************/
				MasterProductType productTypeService = new MasterProductTypeImpl();
			   	List productTypeList =  productTypeService.listProductType(conn);
			   	System.out.println("============YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY====================");
			   	
			   	forward =  "orderProductList"; //orderProductList.html
				ModelAndView model = new ModelAndView(forward);
				model.addObject("productTypeList", productTypeList);
				model.addObject(Constant.SS_ARR_ORDERLIST,arrList);
				model.addObject("msgStep","1");//Step = 1
				//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
				
				System.out.println("============XXXXXXXXXXXXXXXXXXXXXXXXXXXXXx:"+arrList.size());
				Common.close(conn);
				conn = null;
				return model;
			}catch(Exception e){
			    forward = "error100"; //error100.jsp
			    System.out.println(clazzName+":"+e.getMessage());
			    System.out.println(e.toString());
				 try{
					 if(conn!=null){
						Common.close(conn);
					 }
					 conn = null;
				    //Close connection
				 }catch(Exception ex){}
				 return new ModelAndView(forward);
		     }finally{
				 try{
					 if(conn!=null){
						Common.close(conn);
					 }
					 conn = null;
				    //Close connection
				 }catch(Exception ex){}
		     }
	}	
	
	//Step = 1
	@RequestMapping("/orderProductDelete")
	public ModelAndView doOrderDeleteAction(HttpServletRequest request) {			
			Connection conn = null;
			String forward = "";
			try{		
				    System.out.println("=========>Show  orderProductDelete.html");
					printOutParam(request);
					/**************
					* Verify Login or Require Login Only
					/**************/
				    HttpSession session = request.getSession(false); 
					//session check login			   
					if(Utilize.verifyLogin(session)){
					   //case Not login
						forward =  "redirect:/login.html";
						System.out.println("========XXXXXXXXXXXXXXXXXXXXXXXXx Login.html");
						return new ModelAndView(forward);
					}
		
					Object oby = new Object();			    						
					ArrayList arrList = new ArrayList();
				   	try{
				   		oby = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
				     	arrList = (ArrayList)oby; 
				   	}catch(Exception e){
					    System.out.println("<<------------Exception View");
			    	}  					
					//*********************************************		
					
				   	String productId = request.getParameter("productId")==null?"0":request.getParameter("productId");
				   	/********** Connection DB *************/
					conn = Common.open();
					Common.defaultTransaction(conn);
					/********** Connection DB *************/

					/*******New Object ****************/
					if(arrList!=null && arrList.size()>0){
						System.out.println("==>arrList :"+arrList.size());
						ProductForm  prod = null;
					    int i = 0;
						System.out.println("=========== process.");
						Iterator it=arrList.iterator();
					    while(it.hasNext()){
						    prod =(ProductForm)it.next();   
						    System.out.println("==>productId :"+prod.getProductId());
						  
			           		if(prod.getProductId().equals(productId)){
			           			arrList.remove(i);
				      			System.out.println("==Delete Remove productId :"+productId);
				      			System.out.println("==Remove seq:"+i);
				    		    break;
			         		}
						    System.out.println("=========== process555.");
					        i++;
					      }//#While Loop
					}
					System.out.println("=========== 7777777777.");

					MasterProductType productTypeService = new MasterProductTypeImpl();
				   	List productTypeList =  productTypeService.listProductType(conn);
				   	
				   	System.out.println("============Order productForm List=====================");				   	
				   	forward =  "orderProductList"; //orderProductList.html
					ModelAndView model = new ModelAndView(forward);
					model.addObject("productTypeList", productTypeList);
					model.addObject(Constant.SS_ARR_ORDERLIST,arrList);
					model.addObject("msgStep","1");//Step = 1
					//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
					
					Common.close(conn);
					conn = null;
					return model;
				}catch(Exception e){
				    forward = "error100"; //error100.jsp
				    System.out.println(clazzName+":"+e.getMessage());
				    System.out.println(e.toString());
					 try{
						 if(conn!=null){
							Common.close(conn);
						 }
						 conn = null;
					    //Close connection
					 }catch(Exception ex){}
					 return new ModelAndView(forward);
			     }finally{
					 try{
						 if(conn!=null){
							Common.close(conn);
						 }
						 conn = null;
					    //Close connection
					 }catch(Exception ex){}
			 }			
	}
	//TODO: step by step
	//1. get session
	//2. Generate ORDER_INVOICE_ID
	//3. CREATE ORDER _RECORD (Master table)
	//4. CREATE ORDER ITEMS  (Second table)
	//5. SHOW  INVICE ORDER FORM PAYIN 
	// Step 3
	//*********mapping url path
	//@RequestMapping("/orderConfirm")
	//public ModelAndView doConfirmOrderAction(HttpServletRequest request) {
	
	@RequestMapping(value = "/orderConfirm", method = RequestMethod.POST)
	public ModelAndView doConfirmOrderAction(HttpServletRequest request,
			@ModelAttribute("ChangeAddressCustForm") ChangeAddressCustForm chgCustForm, BindingResult result) {
		HttpSession session = request.getSession(false); 				
	    System.out.println("=========>Show  orderConfirm.html");
		printOutParam(request);
		
		Connection conn = null;
		String forward = "";
		try{
		   	/********** Connection DB *************/
			conn = Common.open();
			//Common.defaultTransaction(conn);
			Common.beginTransaction(conn);
			/********** Connection DB *************/
			System.out.println("========BeginTransaction==============");
			 String INVOICE_ID ="";
			 
			Object obj = new Object();			    						
			ArrayList arrList = new ArrayList();
			try{
			   obj = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
			   arrList = (ArrayList)obj; 
			}catch(Exception e){
			    System.out.println("<<------------Exception View XXXXXXXXXXXXXX");
		   }  	
			
			/*********Replace new quantity  product */
			/*List arrNewList = new ArrayList();*/
			//ProductForm objForm = null;	
			if(arrList!=null && arrList.size()>0){	
				UserService usrService = new UserServiceImpl();
				
				//Update Address & Customer Name
				Customer custObj = new Customer();
				custObj.setCustomerId(chgCustForm.getCustomerId());
				custObj.setFirstName(chgCustForm.getFirstName());
				custObj.setLastName(chgCustForm.getLastName());
				custObj.setEmail(chgCustForm.getEmail());
				custObj.setMobile(chgCustForm.getMobile());
				
				int updCust = usrService.UpdateCustomer(conn, custObj);
				System.out.println("-->updCust :"+updCust);
				
				
				CustomerAddress addressObj = new CustomerAddress();
				addressObj.setAddressDesc(chgCustForm.getAddressDesc());
				addressObj.setAddressId(chgCustForm.getAddressId());
				int updAddress = usrService.UpdateCustomerAddress(conn, addressObj);
				System.out.println("-->updAddress :"+updAddress);
			   	//Log.debug("-->Starting transOrder.");	
			   	INVOICE_ID =  GenerateAutoID.getAutoIdFromTable(conn,Constant.FIELD_ORDER_ID,Constant.TABLE_ORDER);  
				System.out.println("-->after generate INVOID_ID:"+INVOICE_ID);	
			   	
				TransOrderProduct transOrderService = new TransOrderProductImpl();			     		
			   	int Ins = transOrderService.InsertTransOrder(conn, arrList, INVOICE_ID);
			   	
			   	if(Ins == -1){//rollback
			   		Common.rollbackTransaction(conn); 
			   	}
			   	System.out.append("====== Ins :::"+Ins);
			   	//1.if isOrder GOTO  PAYMENT PAGE			     		
			   	//request.setAttribute("arrOrderList", arrList);
				synchronized(session) {
					session.removeAttribute(Constant.SS_ARR_ORDERLIST);//remove
					System.out.println("xxxxxx=========== Delete Session order.");
				}
				arrList = null;
				arrList = new ArrayList();//Clean Session
			}

			MasterProductType productTypeService = new MasterProductTypeImpl();
			List productTypeList =  productTypeService.listProductType(conn);
			System.out.println("============Order productForm List=====================");				   	
			
			forward =  "orderComplete"; //orderComplete.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject(Constant.SS_ARR_ORDERLIST,arrList);//set addToCart of product
			model.addObject("msgStep","3");//Step = 1
			//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
			
			Common.commitTransaction(conn);
			System.out.println("========CommitTransaction=====");	
			Common.close(conn);
			conn = null;
			return model;
		}catch(Exception e){
			forward = "error100"; //error100.jsp
			System.out.println(clazzName+":"+e.getMessage());
			System.out.println(e.toString());
			 try{
				
				Common.rollbackTransaction(conn); 
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			}catch(Exception ex){}
			return new ModelAndView(forward);
		}finally{
			 try{
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			   }catch(Exception ex){}
		 }
     }

	/******************
	 * orderChangeAdress
	 * Step = 2 
	 *****************/
	@RequestMapping("/orderChangeAdress") 
	public ModelAndView doChangeOrderAdressAction(HttpServletRequest request) {
	
		HttpSession session = request.getSession(false); 				
	    System.out.println("=========>Show  orderChangeAdress.html");
		printOutParam(request);
		
		Connection conn = null;
		String forward = "";
		try{
		   	/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);			
			/********** Connection DB *************/
	 
			Object obj = new Object();			    						
			ArrayList arrList = new ArrayList();
			try{
			   obj = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
			   arrList = (ArrayList)obj; 
			}catch(Exception e){
			    System.out.println("<<------------Exception View XXXXXXXXXXXXXX");
		   }  	
			
			/*********Replace new quantity  product */
			List arrNewList = new ArrayList();
			ProductForm objForm = null;	
			if(arrList!=null && arrList.size()>0){	
				ProductForm  prod = null;
				int i = 0;
				String quantity = "";
				    
				System.out.println("=========== process.");
				Iterator it=arrList.iterator();
				while(it.hasNext()){
				    prod =(ProductForm)it.next(); 
				    quantity = request.getParameter("quantity"+i);    
					        
				    System.out.println("==>prod :"+prod.getProductId());
				    System.out.println("==>quantity :"+quantity);
					    	
				    objForm = new ProductForm();
				    //Set items 
			        //objForm.setProductItems(5);
				    objForm.setProductItems(Integer.parseInt(quantity));
				    objForm.setProductId(prod.getProductId());
				    objForm.setProductName(prod.getProductName());
				    objForm.setProductSize(prod.getProductSize());
				    objForm.setProductDesc(prod.getProductDesc());
				    objForm.setProductImageName(prod.getProductImageName());
				    objForm.setProductQuantity(prod.getProductQuantity());
				    objForm.setProductColor(prod.getProductColor());
				    objForm.setProductPrice(prod.getProductPrice());
				    objForm.setProductSale(prod.getProductSale());
				    objForm.setProductLastUpdate(prod.getProductLastUpdate());
				    objForm.setAdminId(prod.getAdminId());
				    objForm.setTypeProductId(prod.getTypeProductId());
				    objForm.setTypeProductName(prod.getTypeProductName());
				    System.out.println("=========== process3333.");
				    //arrList.remove(i); //remove old
				    System.out.println("=========== process444.");
				    arrNewList.add(objForm);//add new item
				    i++;
				}//#While Loop				 
			}

			MasterProductType productTypeService = new MasterProductTypeImpl();
			List productTypeList =  productTypeService.listProductType(conn);
			
			
			Customer objCust = new Customer();
			Object obj2 = session.getAttribute(Constant.SS_CUSTOMER_PROFILE);
			if(obj2!=null){
				objCust = (Customer)obj2;
			}	
			//-----------------------------------
			CustomerAddress custAddress = new CustomerAddress();
			Object obj1 = session.getAttribute(Constant.SS_ADDRESS);
			if(obj1!=null){
				custAddress = (CustomerAddress)obj1;
			}
			ChangeAddressCustForm  addressForm = new ChangeAddressCustForm();
			addressForm.setCustomerId(custAddress.getCustomerId());
			addressForm.setAddressId(custAddress.getAddressId());
			addressForm.setAccountId(Integer.parseInt(custAddress.getAddressId()));
			addressForm.setFirstName(objCust.getFirstName());
			addressForm.setLastName(objCust.getLastName());
			addressForm.setMobile(objCust.getMobile());
			addressForm.setEmail(objCust.getEmail());

			addressForm.setAddressDesc(custAddress.getAddressDesc());

			System.out.println("addressForm============ :"+addressForm.getAddressDesc());
			//======================
			
			System.out.println("============ orderChangeAddress.jsp =====================");				   	
			
			forward =  "orderChangeAddress"; //orderComplete.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject(Constant.SS_ARR_ORDERLIST,arrNewList);//set addToCart of product
			model.addObject("ChangeAddressCustForm",addressForm);
			
			model.addObject("msgStep","2");//Step = 1
			//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
		
			System.out.println("========xxxxxxxxxxxxxx=====");	
			Common.close(conn);
			conn = null;
			return model;
		}catch(Exception e){
			forward = "error100"; //error100.jsp
			System.out.println(clazzName+":"+e.getMessage());
			System.out.println(e.toString());
			 try{
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			}catch(Exception ex){}
			return new ModelAndView(forward);
		}finally{
			 try{
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			   }catch(Exception ex){}
		 }
     }
	
	
	@RequestMapping("/orderConfirmOld")
	public ModelAndView doConfirmOrderOldAction(HttpServletRequest request) {
	
		HttpSession session = request.getSession(false); 				
	    System.out.println("=========>Show  orderConfirm.html");
		printOutParam(request);
		
		Connection conn = null;
		String forward = "";
		try{
		   	/********** Connection DB *************/
			conn = Common.open();
			//Common.defaultTransaction(conn);
			Common.beginTransaction(conn);
			/********** Connection DB *************/
			System.out.println("========BeginTransaction==============");
			 String INVOICE_ID ="";
			 
			Object obj = new Object();			    						
			ArrayList arrList = new ArrayList();
			try{
			   obj = session.getAttribute(Constant.SS_ARR_ORDERLIST);//arrOrderList
			   arrList = (ArrayList)obj; 
			}catch(Exception e){
			    System.out.println("<<------------Exception View XXXXXXXXXXXXXX");
		   }  	
			
			/*********Replace new quantity  product */
			List arrNewList = new ArrayList();
			ProductForm objForm = null;	
			if(arrList!=null && arrList.size()>0){	
				ProductForm  prod = null;
				int i = 0;
				String quantity = "";
				    
				System.out.println("=========== process.");
				Iterator it=arrList.iterator();
				while(it.hasNext()){
				    prod =(ProductForm)it.next(); 
				    quantity = request.getParameter("quantity"+i);    
					        
				    System.out.println("==>prod :"+prod.getProductId());
				    System.out.println("==>quantity :"+quantity);
					    	
				    objForm = new ProductForm();
				    //Set items 
			        //objForm.setProductItems(5);
				    objForm.setProductItems(Integer.parseInt(quantity));
				    objForm.setProductId(prod.getProductId());
				    objForm.setProductName(prod.getProductName());
				    objForm.setProductSize(prod.getProductSize());
				    objForm.setProductDesc(prod.getProductDesc());
				    objForm.setProductImageName(prod.getProductImageName());
				    System.out.println("=========== process1111.");
				    objForm.setProductQuantity(prod.getProductQuantity());
				    objForm.setProductColor(prod.getProductColor());
				    objForm.setProductPrice(prod.getProductPrice());
				    objForm.setProductSale(prod.getProductSale());
				    objForm.setProductLastUpdate(prod.getProductLastUpdate());
				    System.out.println("=========== process222.");
				    objForm.setAdminId(prod.getAdminId());
				    objForm.setTypeProductId(prod.getTypeProductId());
				    objForm.setTypeProductName(prod.getTypeProductName());
				    System.out.println("=========== process3333.");
				    //arrList.remove(i); //remove old
				    System.out.println("=========== process444.");
				    arrNewList.add(objForm);//add new item
				    System.out.println("=========== process555.");
				    i++;
				}//#While Loop				 

				 
			   	//Log.debug("-->Starting transOrder.");	
			   	INVOICE_ID =  GenerateAutoID.getAutoIdFromTable(conn,Constant.FIELD_ORDER_ID,Constant.TABLE_ORDER);  
				System.out.println("-->after generate INVOID_ID:"+INVOICE_ID);	
			   	
				TransOrderProduct transOrderService = new TransOrderProductImpl();			     		
			   	int Ins = transOrderService.InsertTransOrder(conn, arrNewList, INVOICE_ID);
			   	
			   	if(Ins == -1){//rollback
			   		Common.rollbackTransaction(conn); 
			   	}
			   	System.out.append("====== Ins :::"+Ins);
			   	//1.if isOrder GOTO  PAYMENT PAGE			     		
			   	//request.setAttribute("arrOrderList", arrList);
				synchronized(session) {
					session.removeAttribute(Constant.SS_ARR_ORDERLIST);//remove
					System.out.println("xxxxxx=========== Delete Session order.");
				}
				arrList = null;
				arrList = new ArrayList();
			}

			MasterProductType productTypeService = new MasterProductTypeImpl();
			List productTypeList =  productTypeService.listProductType(conn);
			System.out.println("============Order productForm List=====================");				   	
			
			forward =  "orderComplete"; //orderComplete.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject(Constant.SS_ARR_ORDERLIST,arrList);//set addToCart of product
			model.addObject("msgStep","3");//Step = 1
			//if(objProduct== null){model.addObject("msg", "ไม่มีรายการสินค้า");}	
			
			Common.commitTransaction(conn);
			System.out.println("========CommitTransaction=====");	
			Common.close(conn);
			conn = null;
			return model;
		}catch(Exception e){
			forward = "error100"; //error100.jsp
			System.out.println(clazzName+":"+e.getMessage());
			System.out.println(e.toString());
			 try{
				
				Common.rollbackTransaction(conn); 
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			}catch(Exception ex){}
			return new ModelAndView(forward);
		}finally{
			 try{
				if(conn!=null){
					Common.close(conn);
				}
				conn = null;
				//Close connection
			   }catch(Exception ex){}
		 }
     }
	
	
}
