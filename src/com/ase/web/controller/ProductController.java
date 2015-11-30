package com.ase.web.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ase.dao.service.Common;
import com.ase.dao.service.MasterProduct;
import com.ase.dao.service.MasterProductImpl;
import com.ase.dao.service.MasterProductType;
import com.ase.dao.service.MasterProductTypeImpl;
import com.ase.model.bean.Product;
import com.ase.model.bean.ProductType;
import com.ase.model.domain.LoginForm;
import com.ase.model.domain.ProductForm;
import com.ase.web.util.Constant;
import com.ase.web.util.Utilize;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Controller
public class ProductController {
	static String  clazzName =  "ProductController";
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
	

	//path URL
	@RequestMapping("/productList")
	public ModelAndView doProductListByTypeAction(HttpServletRequest request) {		
		Connection conn = null;
		String forward = "";
		List productTypeList = null;
		List productListByType = null;
		ProductType objProductType = null;
		try{
			//printOutParam(request);
			//HttpSession session = request.getSession(false);
			System.out.println("-------------doProductListByTypeAction,Form Load Controller ------------------");
			String productTypeId = request.getParameter("productTypeId");
			/********** Connection DB *************/
			conn = Common.open();
			Common.defaultTransaction(conn);
			/********** Connection DB *************/
		    
			MasterProductType productTypeService = new MasterProductTypeImpl();
			MasterProduct   productService = new MasterProductImpl();
			/***************************
			*For  Logic
			**************************/
			productTypeList = productTypeService.listProductType(conn);
			System.out.println("productTypeList====="+productTypeList.size());
			
			productListByType = productService.listProductByType(conn, productTypeId);
			System.out.println("productListByType====="+productListByType.size());
			
			objProductType = productTypeService.GetProductType(conn, productTypeId);
			System.out.println("ProductType====="+objProductType.getProductTypeName());
			
			List<String> imageDatas = new ArrayList<String>();
			try {
					for(int i = 0;i < productListByType.size(); i++){
					Product product =(Product) productListByType.get(i);
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
			
			/************************************/	
			forward = "productList"; //home.jsp
			ModelAndView model = new ModelAndView(forward);
			model.addObject("productTypeList", productTypeList);
			model.addObject("productListByType",productListByType);
			model.addObject("imageDatas", imageDatas);
			model.addObject("commandType",objProductType);
			
			if(productListByType.size()==0){
				model.addObject("msg", "ไม่มีรายการสินค้า");
			}	
			System.out.println("==============Successfully==================");
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
	
	//path URL
	@RequestMapping("/productDesc")
	public ModelAndView doProductDescAction(HttpServletRequest request) {		
			Connection conn = null;
			String forward = "";
			List productTypeList = null;
			Product objProduct = null;
			ProductType objProductType = null;
			
			ProductForm  productForm = new ProductForm();
			List<String> imageList = new ArrayList<String>();
			try{
				printOutParam(request);
				//HttpSession session = request.getSession(false);
				System.out.println("-------------doProductDescAction,Form Load Controller ------------------");
				String productId = request.getParameter("productId");
				/********** Connection DB *************/
				conn = Common.open();
				Common.defaultTransaction(conn);
				/********** Connection DB *************/
			    
				MasterProductType productTypeService = new MasterProductTypeImpl();
				MasterProduct   productService = new MasterProductImpl();
				/***************************
				*For  Logic
				**************************/
				objProduct = productService.GetProductDesc(conn, productId);
				if(objProduct!=null){
					objProductType = productTypeService.GetProductType(conn, objProduct.getTypeProductId());
					
					//set to productForm
					productForm.setProductId(objProduct.getProductId());
					productForm.setProductName(objProduct.getProductName());
					productForm.setProductSize(objProduct.getProductSize());
					productForm.setProductDesc(objProduct.getProductDesc());
					productForm.setProductImageName(objProduct.getProductImageName());
					productForm.setProductQuantity(objProduct.getProductQuantity());
					productForm.setProductColor(objProduct.getProductColor());
					productForm.setProductPrice(objProduct.getProductPrice());
					productForm.setProductSale(objProduct.getProductSale());
					productForm.setProductLastUpdate(objProduct.getProductLastUpdate());
					productForm.setAdminId(objProduct.getAdminId());
					productForm.setTypeProductId(objProduct.getTypeProductId());
					productForm.setTypeProductName(objProduct.getTypeProductName());
					productForm.setProductUnitType(objProduct.getProductUnitType());
					//return new ModelAndView("LoginForm", "command",new LoginForm());					
				}
				productTypeList = productTypeService.listProductType(conn);
				
				try {
		            String[] ary = productForm.getProductImageName().split(".jpg");
		            for (String imagePath : ary) {
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
		                imageList.add(Base64.encode(fileBytes));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

				/************************************/	
				forward = "productDesc"; //productDesc.jsp

				ModelAndView model = new ModelAndView(forward);
				model.addObject("productTypeList", productTypeList);
				//model.addObject("productObj",objProduct); productForm
				model.addObject("productForm",productForm);
	            model.addObject("ImageDataList", imageList);
				model.addObject("productTypeObj",objProductType);
				
				if(objProduct== null){
					model.addObject("msg", "ไม่มีรายการสินค้า");
				}	
				System.out.println("==============Successfully==================");
				
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
