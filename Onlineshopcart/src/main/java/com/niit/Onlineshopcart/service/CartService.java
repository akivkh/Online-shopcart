/*package com.niit.Onlineshopcart.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.Onlineshopcart.model.UserModel;
import com.niit.Oshopcartbackend.Dao.CartLineDao;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;
import com.niit.Oshopcartbackend.model.Product;

@Service("cartService")
public class CartService {
	
	@Autowired
	private CartLineDao cartLineDao;
	
	@Autowired
	private HttpSession session;
	
	// return the cart of the user who has logged in 
	private Cart getCart() {
		
		return((UserModel)session.getAttribute("userModel")).getCart();
		
	}
	 
	public List<CartLine> getCartLine(){
		
		Cart cart =this.getCart();
		return cartLineDao.list(cart.getId());
	}

	public String updateCartLine(int cartLineId, int count) {
		
		// fetch the cartLine 
		CartLine cartLine =cartLineDao.get(cartLineId);
		
		if(cartLine == null) {
			return "result=error";
			
		}
		else {
			
			Product  product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();
			
			if(product.getQuantity() <= count) {
				count = product.getQuantity();
				
			}
			cartLine.setProductCount(count);
		    cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice() *count);
		    cartLineDao.update(cartLine);
			Cart cart =this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal +cartLine.getTotal());
			cartLineDao.updateCart(cart);
			return "result=update";
		}
	}
	

}
*/