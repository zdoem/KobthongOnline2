package com.ase.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ase.dao.service.Common;
import com.ase.dao.service.MasterProduct;
import com.ase.dao.service.MasterProductImpl;
import com.ase.dao.service.MasterProductType;
import com.ase.dao.service.MasterProductTypeImpl;
import com.ase.dao.service.UserService;
import com.ase.dao.service.UserServiceImpl;
import com.ase.model.bean.Account;
import com.ase.model.bean.Customer;
import com.ase.model.bean.CustomerAddress;
import com.ase.model.bean.Product;
import com.ase.model.domain.LoginForm;
import com.ase.model.domain.RegisterForm;
import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Controller
public class HomeController {

	static String clazzName = "HomeController";
	static {
		// initial First Request configure datasouce only
		// Use connection db server type Connection pool
		Common.setConfigForConnectionPool("", Constant.DataSourceName);
	}

	// Show all parameter All by pass Request query.
	private static void printOutParam(HttpServletRequest request) {
		String paramNames = "";
		System.out.println("---------[ Parameter List] ------------");
		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			paramNames = (String) e.nextElement();
			System.out.println(paramNames + " = "
					+ request.getParameter(paramNames));
		}
		System.out.println("---------- [Parameter List] -----------");
	}

	// path URL
	@RequestMapping("/home")
	public ModelAndView doHomeAction(HttpServletRequest request) {
		Connection conn = null;
		String forward = "";
		List productTypeList = null;
		List productList = null;
		try {
			// printOutParam(request);
			// HttpSession session = request.getSession(false);
			System.out
					.println("-------------HOME,Form Load Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/

			MasterProductType productTypeService = new MasterProductTypeImpl();
			MasterProduct productService = new MasterProductImpl();
			/***************************
			 * For Logic
			 **************************/
			productTypeList = productTypeService.listProductType(conn);
			System.out.println("productTypeList=====" + productTypeList.size());

			productList = productService.listProduct(conn, Constant.LIMIT_5);
			System.out.println("productList=====" + productList.size());
			List<String> imageDatas = new ArrayList<String>();
			try {
					for(int i = 0;i < productList.size(); i++){
					Product product =(Product) productList.get(i);
					String[] ary = product.getProductImageName().split(".jpg");
					String imagePath = ary[0];
						System.out.println(imagePath);
						File file = new File(imagePath + ".jpg");
						FileInputStream fis = new FileInputStream(file);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						int b;
						byte[] buffer = new byte[1024];
						while ((b = fis.read(buffer)) != -1) {
							bos.write(buffer, 0, b);
						}
						byte[] fileBytes = bos.toByteArray();
						fis.close();
						bos.close();
						imageDatas.add(Base64.encode(fileBytes));
					}
			} catch (Exception e) {
				e.printStackTrace();
			}

			/***********************************/
			// "home.jsp","beanName/AttributeName","Object/List/Container"
			// return new ModelAndView(forward,
			// "productTypeList",productTypeList);
			forward = "home"; // home.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject("productList", productList);
			model.addObject("imageDatas", imageDatas);
			System.out.println("================================");

			Common.close(conn);
			conn = null;
			return model;
		} catch (Exception e) {
			// request.setAttribute("args1", e.getMessage());
			forward = "error100"; // error100.jsp
			System.out.println(clazzName + ":" + e.getMessage());
			System.out.println(e.toString());
			try {
				if (conn != null) {
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
			return new ModelAndView(forward);
		} finally {
			try {
				if (conn != null) {
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
		}
	}

	@RequestMapping("/registerForm")
	public ModelAndView showRegisterForm() {
		System.out
				.println("-------------showRegisterForm  Controller ------------------");
		// mapping LoginForm.jsp file ,bean name,Object Form
		return new ModelAndView("registerForm", "commandRegForm",
				new RegisterForm());
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegisterAction(HttpServletRequest request,
			@ModelAttribute("commandRegForm") RegisterForm regForm,
			BindingResult result) {

		Connection conn = null;
		String forward = "success"; // "redirect:/success.html";
		try {
			printOutParam(request);
			System.out
					.println("-------------save RegisterForm  Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.beginTransaction(conn);
			/********** Connection DB *************/

			/*** New Service or Object Service */
			UserService usrService = new UserServiceImpl();
			/********************
			 * 1.check duplication UserName form account isDubplicate Return to
			 * screen Form 2.Insert into account 3.Insert into customer 4.Insert
			 * into address_customer 5.go to success page 6.Errors sorry page
			 ***************** */

			boolean isDupUsername = usrService.IsDuplicateUserName(conn,
					regForm.getUserId());

			if (isDupUsername) { // TODO: CASE Duplication userName
				return new ModelAndView(
						"registerForm",
						"msg",
						"Username ' "
								+ regForm.getUserId()
								+ " ' มีการใช้งานเเล้ว");
			}

			Account accountObj = new Account();
			accountObj.setUserName(regForm.getUserId());
			accountObj.setUserPassword(regForm.getPassword());
			accountObj.setUserType(Constant.USER_TYPE_USER);
			int insAccount = usrService.InsertAccount(conn, accountObj);

			System.out.println("=========== Insert Account OK : " + insAccount
					+ " ===================");
			Account objAccount = usrService.GetAccount(conn,
					regForm.getUserId());
			Customer custObj = new Customer();
			custObj.setFirstName(regForm.getFirstName());
			custObj.setLastName(regForm.getLastName());
			custObj.setTelephone("");
			custObj.setMobile(regForm.getMobile());
			custObj.setEmail(regForm.getEmail());
			custObj.setFax("");
			custObj.setAccountId(Integer.parseInt(objAccount.getAccountId()));
			int insCustomer = usrService.InsertCustomer(conn, custObj);
			System.out.println("=========== Insert Customer OK : "
					+ insCustomer + " ===================");

			custObj = usrService.GetUserCustomer(conn,
					Integer.parseInt(objAccount.getAccountId()));
			System.out.println("custObj.getCustomerId() ====== :"
					+ custObj.getCustomerId());
			CustomerAddress addObj = new CustomerAddress();
			addObj.setAddressDesc(regForm.getAddressDesc());
			addObj.setCustomerId(custObj.getCustomerId());

			int insCustAddress = usrService.InsertAddress(conn, addObj);
			System.out.println("=========== Insert CustomerAddress OK : "
					+ insCustAddress + " ===================");

			// -------------------
			ModelAndView model = new ModelAndView(forward);
			System.out
					.println("==================Register Commit Transaction =================");
			Common.commitTransaction(conn);
			Common.close(conn);
			conn = null;

			return model;
		} catch (Exception e) {
			// request.setAttribute("args1", e.getMessage());
			forward = "error100"; // error100.jsp
			System.out.println(clazzName + ":" + e.getMessage());
			System.out.println(e.toString());
			try {
				if (conn != null) {
					Common.rollbackTransaction(conn);
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
			return new ModelAndView(forward);
		} finally {
			try {
				if (conn != null) {
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
		}
	}

	// ********* orderProductList :: Step#1
	@RequestMapping(value = "/userProfile")
	// , method = RequestMethod.GET,POST
	public ModelAndView doOrdderListAction(HttpServletRequest request) {
		Connection conn = null;
		String forward = ""; // userProfile.jsp
		try {
			System.out.println("--->Order  userProfile.html");
			printOutParam(request);

			/**************
			 * Verify Login or Require Login Only /
			 **************/
			HttpSession session = request.getSession(false);
			// session check login
			if (Utilize.verifyLogin(session)) {
				// case Not login
				forward = "redirect:/login.html";
				System.out
						.println("========XXXXXXXXXXXXXXXXXXXXXXXXx Login.html");
				return new ModelAndView(forward);
			}
			Object oby = new Object();
			ArrayList arrList = new ArrayList();
			try {
				oby = session.getAttribute(Constant.SS_ARR_ORDERLIST);// arrOrderList
				arrList = (ArrayList) oby;
			} catch (Exception e) {
				System.out.println("<<------------Exception View");
			}
			// *********************************************
			if (arrList != null && arrList.size() > 0) {
				System.out
						.println("============XXXXXXXXXXXXXXXXXXXXXXXXXXXXXx====================");
			} else {
				arrList = new ArrayList();
				System.out
						.println("============TTTTTTTTTTTTTTTTTTTTTTT====================");
			}
			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/

			/******* New Object ****************/
			MasterProductType productTypeService = new MasterProductTypeImpl();
			List productTypeList = productTypeService.listProductType(conn);
			System.out
					.println("============YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY====================");

			// Get user profile
			Object obj1 = session.getAttribute(Constant.SS_CUSTOMER_PROFILE);
			Customer usrCustomer = null;
			if (obj1 != null) {
				usrCustomer = (Customer) obj1;
			}

			Object obj2 = session.getAttribute(Constant.SS_USEROBJ);
			Account userAccount = null;
			if (obj2 != null) {
				userAccount = (Account) obj2;
			}

			Object obj3 = session.getAttribute(Constant.SS_ADDRESS);
			CustomerAddress custAddress = null;
			if (obj3 != null) {
				custAddress = (CustomerAddress) obj3;
			}

			RegisterForm regForm = new RegisterForm();
			regForm.setCustomerId(usrCustomer.getCustomerId() + "");
			regForm.setUserId(userAccount.getUserName());
			regForm.setPassword(userAccount.getUserPassword());
			regForm.setFirstName(usrCustomer.getFirstName());
			regForm.setLastName(usrCustomer.getLastName());
			regForm.setMobile(usrCustomer.getMobile());
			regForm.setEmail(usrCustomer.getEmail());
			regForm.setAddressDesc(custAddress.getAddressDesc());

			forward = "userProfile"; // userProfile.html
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject(Constant.SS_ARR_ORDERLIST, arrList);
			model.addObject("commandRegForm", regForm);

			System.out.println("============XXXXXXXXXXXXXXXXXXXXXXXXXXXXXx:"
					+ arrList.size());
			Common.close(conn);
			conn = null;
			return model;
		} catch (Exception e) {
			forward = "error100"; // error100.jsp
			System.out.println(clazzName + ":" + e.getMessage());
			System.out.println(e.toString());
			try {
				if (conn != null) {
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
			return new ModelAndView(forward);
		} finally {
			try {
				if (conn != null) {
					Common.close(conn);
				}
				conn = null;
				// Close connection
			} catch (Exception ex) {
			}
		}
	}

	@RequestMapping("/testSuccess")
	public ModelAndView doTestAction(HttpServletRequest request) {
		// String message = "Hello World, Spring 3.0!";
		/*
		 * try{ }catch(Exception e){ }finally{ }
		 */
		// EchoParamRQ(request);
		System.out.println("Test Controller...");
		return new ModelAndView("success", "command", "hello Spring MVC");
	}

	// path URL
	@RequestMapping("/homeTest")
	public ModelAndView doHomeTestAction(HttpServletRequest request) {
		// String message = "Hello World, Spring 3.0!";
		/*
		 * try{ }catch(Exception e){ }finally{ }
		 */
		// EchoParamRQ(request);
		System.out.println("Test Controller...");
		return new ModelAndView("home_test", "command", "hello Spring MVC");
		// "fileName.jsp","beanName/AttributeName","Object/List/Container"
	}
	
	@RequestMapping("/companyProfile")
	public ModelAndView doCompanyProfileAction(HttpServletRequest request) {		
		Connection conn = null;
		String forward = "";
		List productTypeList = null;
		try{
			//printOutParam(request);
			//HttpSession session = request.getSession(false);
			System.out.println("-------------doCompanyProfileAction Load Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/
		    
			MasterProductType productTypeService = new MasterProductTypeImpl();

			/***************************
			*For  Logic
			**************************/
			productTypeList = productTypeService.listProductType(conn);
			System.out.println("productTypeList====="+productTypeList.size());
			
			/***********************************/			
			forward = "companyProfile"; //home.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			System.out.println("================================");
			
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
	
	@RequestMapping("/contact")
	public ModelAndView doContactAction(HttpServletRequest request) {		
		Connection conn = null;
		String forward = "";
		List productTypeList = null;
		try{
			//printOutParam(request);
			//HttpSession session = request.getSession(false);
			System.out.println("-------------doContactAction Load Controller ------------------");

			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/
		    
			MasterProductType productTypeService = new MasterProductTypeImpl();

			/***************************
			*For  Logic
			**************************/
			productTypeList = productTypeService.listProductType(conn);
			System.out.println("productTypeList====="+productTypeList.size());
			
			/***********************************/			
			forward = "contact"; //home.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			System.out.println("================================");
			
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

}
