 package com.niit.Oshopcartbackend.Dao;

import com.niit.Oshopcartbackend.model.Address;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.User;

public interface UserDao {

	// add an user
	boolean addUser(User user);
	User getByEmail(String email);
	
	// add an address
	boolean addAddress(Address address);
	
	// add an cart
	boolean updateCart(Cart cart);
}
