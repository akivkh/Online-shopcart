package com.niit.Onlineshopcart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.Onlineshopcart.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	private final static Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private  CartService cartService;

	@RequestMapping("/show")
	public ModelAndView showCart(@RequestParam(name="result",required =false)String result) {
		
		ModelAndView mv =new ModelAndView("page");
		   mv.addObject("title", "Shopping Cart");
			mv.addObject("userClickShowCart", true);
		
		if(result!=null) {
			
			switch(result) {
			
			case "updated":
				mv.addObject("message","CartLine has been updated successfully!");
				break;
				
			case "added":
				mv.addObject("message","CartLine has been added successfully!");
				break;
				
			case "deleted":
				mv.addObject("message","CartLine has been deleted successfully!");
				break;
				
			case "maximum":
				mv.addObject("message","CartLine has reached to maximum count!");
				break;
				
			case "unavailable":
				mv.addObject("message","Product quantity is not available!");
				break;
				
			case "error":
				mv.addObject("message","Something went Wrong");
				break;
			
			}
		}
		else {
			String response = cartService.validateCartLine();
			if(response.equals("result=modified")) {
				mv.addObject("message", "one or more itmes inside cart has beeen Modified! ");
			}
		}

		mv.addObject("cartLines", cartService.getCartLines());
		return mv;
	}
	
	
	@RequestMapping("/{cartLineId}/update")
	public String updateCartLine(@PathVariable int cartLineId, @RequestParam int count) {
		
		String response = cartService.manageCartLine(cartLineId, count);
		return "redirect:/cart/show?"+response;
	} 
 
	@RequestMapping("/{cartLineId}/delete")
	public String deleteCartLine(@PathVariable int cartLineId) {
		
		String response = cartService.deleteCartLine(cartLineId);
		return "redirect:/cart/show?"+response;
	}
	@RequestMapping("/add/{productId}/product")
	public String addCartLine(@PathVariable int productId) {
		
		String response = cartService.addCartLine(productId);
		return "redirect:/cart/show?"+response;
	}
	
	/*
	 * after validating it redirect to checkout
	 * if result received is success procced to checkout
	 * else display the message to the user about the change in cart page
	 * */
	@RequestMapping("/validate")
	public String validateCart() {
		String response =cartService.validateCartLine();
		if(!response.equals("result=success")) {
			return "redirect:/cart/show?"+response;
		}
		else {
			return "redirect:/cart/checkout";
		}
	}
}
