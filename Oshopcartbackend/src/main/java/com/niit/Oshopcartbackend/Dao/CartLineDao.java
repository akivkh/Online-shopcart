package com.niit.Oshopcartbackend.Dao;

import java.util.List;

import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;

public interface CartLineDao {
  
	public CartLine get(int id);
	public boolean add(CartLine cartLine);
	public boolean update(CartLine cartLine);
	public boolean delete(CartLine cartLine);
	public List<CartLine> list(int cartId);
	
	
	// other bussiness method related to the cart lines
	
	public List<CartLine> listAvailable(int cartId);
	public CartLine getByCartAndProduct(int cartId, int productId);
	

	// add an cart
	boolean updateCart(Cart cart);
	boolean addCart(Cart cart);
	
	
}
