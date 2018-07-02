 package com.niit.Oshopcartbackend.Dao;

import java.util.List;

import com.niit.Oshopcartbackend.model.Address;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.User;

public interface UserDao {

	// add an user
	boolean addUser(User user);
	boolean updateUser(User user);
	User getByEmail(String email);
	
	// add an updating a new address
	Address getAddress(int addressId);
	boolean addAddress(Address address);
	Boolean updateAddress(Address address);
	Address getBillingAddress(int userId);
	List<Address> listShippingAddresses(int UserId);
	boolean updateCart(Cart cart);


	
}
