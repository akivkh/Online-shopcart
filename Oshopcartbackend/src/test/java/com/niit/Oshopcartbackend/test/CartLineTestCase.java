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
	public void testAddNewCartLine() {
		// 1.get the user
		user = userDao.getByEmail("ar@gmail.com");
		
		// 2. fetch the cart
		cart =user.getCart();
		
		//3. get the product
		product = productDao.get(1);
		
		//4. create a new cartLine
		cartLine = new CartLine();
		
		cartLine.setBuyingPrice(product.getUnitPrice());
		
		cartLine.setProductCount(cartLine.getProductCount() + 1);
		
		cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
		
		cartLine.setAvailable(true);
		
		cartLine.setCartId(cart.getId());
		
		cartLine.setProduct(product);
		
		assertEquals("Failed to add the cartLine", true, cartLineDao.add(cartLine));
		
		// update the cart
		cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
		cart.setCartLines(cart.getCartLines() +1);
		
		assertEquals("Failed to update  the cartLine", true, cartLineDao.updateCart(cart));
	}
	

}
