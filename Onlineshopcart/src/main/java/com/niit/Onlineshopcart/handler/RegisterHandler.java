package com.niit.Onlineshopcart.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.niit.Onlineshopcart.model.RegisterModel;
import com.niit.Oshopcartbackend.Dao.UserDao;
import com.niit.Oshopcartbackend.model.Address;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.User;

@Component
public class RegisterHandler {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegisterModel init() {
		return new RegisterModel();
	}

	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}

	public String ValidateUser(User user, MessageContext error) {

		String transitionValue = "success";
  // checking if password matches confirm password
		System.out.println("intialize password match confirm");
		if (!(user.getPassword().equals(user.getConfirmPassword()))) {

			error.addMessage(new MessageBuilder()
					  .error()
					    .source("confirmPassword")
					       .defaultText("Password does not match the confirm password")
					         .build()
					         );
			transitionValue = "failure";
		}
		
		// check the  uniqueness of the email id
		System.out.println("intialize uniqueness of email id");
		if(userDao.getByEmail(user.getEmail())!=null) {
			error.addMessage(new MessageBuilder()
					  .error()
					    .source("email")
					       .defaultText("Email Address is already used!")
					         .build());
			transitionValue ="failure";
		}
		
		return transitionValue;
	}

	public String saveAll(RegisterModel model) {
		String transitionValue = "success";

		// fetch the user
		User user = model.getUser();
		if (user.getRole().equals("USER")) {
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}
		// encode the password
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	
		// save the user
         userDao.addUser(user);

         System.out.println("getting address..");
		// get the address
		Address billing = model.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);

		// save the address
		userDao.addAddress(billing);
		return transitionValue;
	}

}
