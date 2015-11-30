package com.ase.model.domain;

import java.util.List;

public class ProductForm {
	
	 private String productId;
	 private String productName;
	 private String productSize;
	 private String productDesc;
	 private String productImageName;
	 private Integer productQuantity;
	 private String productUnitType;
	 private String productColor;	 
	 private Double productPrice;
	 private Double productSale;
	 private String productLastUpdate;
	 //private List<Integer> productItemsList;
	 private Integer productItems;
	 private String adminId;
	 private String typeProductId;
	 private String typeProductName;
	 private String cmd;
	 

	public Integer getProductItems() {
		return productItems;
	}
	public void setProductItems(Integer productItems) {
		this.productItems = productItems;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Double getProductSale() {
		return productSale;
	}
	public void setProductSale(Double productSale) {
		this.productSale = productSale;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSize() {
		return productSize;
	}
	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductImageName() {
		return productImageName;
	}
	public void setProductImageName(String productImageName) {
		this.productImageName = productImageName;
	}
	public Integer getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductLastUpdate() {
		return productLastUpdate;
	}
	public void setProductLastUpdate(String productLastUpdate) {
		this.productLastUpdate = productLastUpdate;
	}

	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getTypeProductId() {
		return typeProductId;
	}
	public void setTypeProductId(String typeProductId) {
		this.typeProductId = typeProductId;
	}
	public String getTypeProductName() {
		return typeProductName;
	}
	public void setTypeProductName(String typeProductName) {
		this.typeProductName = typeProductName;
	}
	public String getProductUnitType() {
		return productUnitType;
	}
	public void setProductUnitType(String productUnitType) {
		this.productUnitType = productUnitType;
	}
	 
	 
}
