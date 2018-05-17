package com.niit.Oshopcartbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.Oshopcartbackend.Dao.UserDao;
import com.niit.Oshopcartbackend.model.Address;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	private static UserDao userDao;
	private User user=null;
	private Cart cart= null;
	private Address address;
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.Oshopcartbackend");
		context.refresh();
		
		userDao =(UserDao)context.getBean("userDao");
	}
	/*@Test
	public void testAdd() {
	 
		user = new User();
		user.setFirstName("Ramesh");
		user.setLastName("Sharma");
		user.setEmail("rs@gmail.com");
		user.setContactNumber("1234567890");
		user.setRole("USER");
		user.setPassword("123456789");
				
		
		//add the user
		assertEquals("Failed to add user,",true, userDao.addUser(user));
	
		address =new Address();
		address.setAddressLineOne("101/B Teacher Colony Sidhart Nagar");
		address.setAddressLineTwo("Near Petrol Pump");
		address.setCity("Lucknow");
		address.setState("Uttar Pradesh");
		address.setCountry("India");
		address.setPostalCode("140001");
		address.setBilling(true);
		
		// link the user with the address using user id
		address.setUserId(user.getId());
	
		//add the Address
		assertEquals("Failed to add address,",true, userDao.addAddress(address));
	
		if(user.getRole().equals("USER")) {
			//create a cart for this user
			cart= new Cart();
			cart.setUser(user);
			
			// add the cart
			assertEquals("Failed to add cart,",true, userDao.addCart(cart));
		
			// add a shipping address for this user
			address =new Address();
			address.setAddressLineOne("101/B Teacher Colony Sidhart Nagar");
			address.setAddressLineTwo("Near Petrol Pump");
			address.setCity("Lucknow");
			address.setState("Uttar Pradesh");
			address.setCountry("India");
			address.setPostalCode("140001");
			//set shipping to true
			address.setShipping(true);
			
			// link it with the user
			address.setUserId(user.getId());
			
			// add the shipping address
			assertEquals("Failed to add shipping address",true, userDao.addAddress(address));
		}
	
	}*/
	
/*	
	@Test
	public void testAdd() {
	 
		user = new User();
		user.setFirstName("Ramesh");
		user.setLastName("Sharma");
		user.setEmail("rs@gmail.com");
		user.setContactNumber("1234567890");
		user.setRole("USER");
		user.setPassword("123456789");
				
	    if(user.getRole().equals("USER")) {
			//create a cart for this user
			cart= new Cart();
			cart.setUser(user);
			
			// attach cart with the user
			user.setCart(cart);
		}
		//add the user
	      assertEquals("Failed to add user,",true, userDao.addUser(user));
	}*/
	
	@Test
	public void testUpdateCart() {
		
		//fetch the user by its email
		user = userDao.getByEmail("rs@gmail.com");
		
		
		// get the cart of the user
		cart = user.getCart();
		
		cart.setGrandTotal(5555);
		cart.setCartLines(2);
		assertEquals("Failed to update the cart!", true, userDao.updateCart(cart));
	}
	
}
