package com.ase.dao.service;

import java.util.List;
import java.sql.Connection;
import com.ase.model.bean.ProductType;

public interface MasterProductType {
	public List listProductType(Connection conn) throws Exception;
	public ProductType GetProductType(Connection conn,String productTypeId) throws Exception;
}
