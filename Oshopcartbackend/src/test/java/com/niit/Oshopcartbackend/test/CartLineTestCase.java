package com.niit.Oshopcartbackend.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.Oshopcartbackend.Dao.CartLineDao;
import com.niit.Oshopcartbackend.Dao.ProductDao;
import com.niit.Oshopcartbackend.Dao.UserDao;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;
import com.niit.Oshopcartbackend.model.Product;
import com.niit.Oshopcartbackend.model.User;

public class CartLineTestCase {
	
	private static AnnotationConfigApplicationContext context;

	private static CartLineDao cartLineDao;
	private static ProductDao productDao;
	private static UserDao userDao;
	
	
	private Product product =null;
	private User user =null;
	private Cart cart =null;
	private CartLine cartLine =null;
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.Oshopcartbackend");
		context.refresh();
		productDao = (ProductDao)context.getBean("productDao");
		userDao = (UserDao)context.getBean("userDao");
		cartLineDao = (CartLineDao)context.getBean("cartLineDao");
	}
	@Test
	public void testAddCartLine() {
		
		// fetch the user and then cart of that user
		User user = userDao.getByEmail("rm@gmail.com");		
		Cart cart = user.getCart();
		
		// fetch the product 
		Product product = productDao.get(2);
		
		// Create a new CartLine
		cartLine = new CartLine();
		cartLine.setCartId(cart.getId());
		cartLine.setProduct(product);
		cartLine.setProductCount(1);
		
		double oldTotal = cartLine.getTotal();		
		
		cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());
		
		cart.setCartLines(cart.getCartLines() + 1);
		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
		
		assertEquals("Failed to add the CartLine!",true, cartLineDao.add(cartLine));
		assertEquals("Failed to update the cart!",true, userDao.updateCart(cart));
		
	}
	

	
   @Test
	public void testUpdateCartLine() {
		// fetch the user and then cart of that user
		User user = userDao.getByEmail("absr@gmail.com");		
		Cart cart = user.getCart();
				
		cartLine = cartLineDao.getByCartAndProduct(cart.getId(), 2);
		
		cartLine.setProductCount(cartLine.getProductCount() + 1);
				
		double oldTotal = cartLine.getTotal();
				
		cartLine.setTotal(cartLine.getProduct().getUnitPrice() * cartLine.getProductCount());
		
		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
		
		assertEquals("Failed to update the CartLine!",true, cartLineDao.update(cartLine));	
		
	}
	
}