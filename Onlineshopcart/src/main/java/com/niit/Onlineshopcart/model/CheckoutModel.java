package com.niit.Onlineshopcart.model;

import java.io.Serializable;
import java.util.List;

import com.niit.Oshopcartbackend.model.Address;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;
import com.niit.Oshopcartbackend.model.OrderDetail;
import com.niit.Oshopcartbackend.model.User;

public class CheckoutModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Address shipping;
	private Cart cart;
	private List<CartLine> cartLines;
	private OrderDetail orderDetail;
	private double checkoutTotal;
	
	/*
	 * getter or setter field
	 * */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getShipping() {
		return shipping;
	}
	public void setShipping(Address shipping) {
		this.shipping = shipping;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public List<CartLine> getCartLines() {
		return cartLines;
	}
	public void setCartLines(List<CartLine> cartLines) {
		this.cartLines = cartLines;
	}
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetails) {
		this.orderDetail = orderDetails;
	}
	public double getCheckoutTotal() {
		return checkoutTotal;
	}
	public void setCheckoutTotal(double checkoutTotal) {
		this.checkoutTotal = checkoutTotal;
	}
	
	

}
