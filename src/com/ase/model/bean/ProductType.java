package com.ase.model.bean;

public class ProductType {
	private String productTypeId;
	private String productTypeName;
	private String productTypeImageName;
	private String productTypeDesc;
	private String productTypeLastUpdate;
	private String prodcutTypeCreate;
	private String adminId;
	
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getProductTypeImageName() {
		return productTypeImageName;
	}
	public void setProductTypeImageName(String productTypeImageName) {
		this.productTypeImageName = productTypeImageName;
	}
	public String getProductTypeDesc() {
		return productTypeDesc;
	}
	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
	}
	public String getProductTypeLastUpdate() {
		return productTypeLastUpdate;
	}
	public void setProductTypeLastUpdate(String productTypeLastUpdate) {
		this.productTypeLastUpdate = productTypeLastUpdate;
	}
	public String getProdcutTypeCreate() {
		return prodcutTypeCreate;
	}
	public void setProdcutTypeCreate(String prodcutTypeCreate) {
		this.prodcutTypeCreate = prodcutTypeCreate;
	}

}
