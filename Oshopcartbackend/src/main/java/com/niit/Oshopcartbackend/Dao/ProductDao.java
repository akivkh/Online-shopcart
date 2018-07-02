package com.niit.Oshopcartbackend.Dao;

import java.util.List;

import com.niit.Oshopcartbackend.model.Product;

public interface ProductDao {

	Product get(int productId);
	List<Product> list();
	boolean add(Product product);
	boolean update(Product product);
	boolean delete(Product product);
	
	//another method to get list of below products
	
	List<Product> listActiveProducts(); //get list of active product
	List<Product> listActiveProductsByCategory(int categoryId); //to find list of active product by category 
	List<Product> getLatestActiveProducts(int count);  //to get latest producy by count
	

	List<Product> getProductsByParam(String param, int count);
}
