package com.ase.dao.service;

import java.sql.Connection;
import java.util.List;
import com.ase.model.bean.Product;

public interface MasterProduct {
	  public List  listProduct(Connection conn,String limit) throws Exception;
	  public List  listProductByType(Connection conn,String productTypeId) throws Exception;
	  public Product GetProductDesc(Connection conn,String productId) throws Exception;
}
