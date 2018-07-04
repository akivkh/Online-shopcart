package com.niit.Oshopcartbackend.Dao;

import java.util.List;

import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;
import com.niit.Oshopcartbackend.model.OrderDetail;

public interface CartLineDao {


		public List<CartLine> list(int cartId);
		public CartLine get(int id);	
		public boolean add(CartLine cartLine);
		public boolean update(CartLine cartLine);
		public boolean delete(CartLine cartLine); 
		 
		// fetch the CartLine based on cartId and productId
		public CartLine getByCartAndProduct(int cartId, int productId);		
			
		// updating the cart
		boolean updateCart(Cart cart);
		
		// list of available cartLine
		public List<CartLine> listAvailable(int cartId);
		
		// adding order details
		boolean addOrderDetail(OrderDetail orderDetail);

		
	}