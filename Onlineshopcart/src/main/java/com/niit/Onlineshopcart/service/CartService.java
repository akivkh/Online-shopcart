package com.niit.Onlineshopcart.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.Onlineshopcart.model.UserModel;
import com.niit.Oshopcartbackend.Dao.CartLineDao;
import com.niit.Oshopcartbackend.Dao.ProductDao;
import com.niit.Oshopcartbackend.model.Cart;
import com.niit.Oshopcartbackend.model.CartLine;
import com.niit.Oshopcartbackend.model.Product;

@Service("cartService")
public class CartService {

	@Autowired
	private CartLineDao cartLineDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	private HttpSession session;
 
	// return the cart of the user who has logged in
	private Cart getCart() {

		return ((UserModel) session.getAttribute("userModel")).getCart();

	}

	// return the entire cart lines
	public List<CartLine> getCartLines() {
		return cartLineDao.list(getCart().getId());
	}
   // to update the cart count
	public String manageCartLine(int cartLineId, int count) {
		CartLine cartLine = cartLineDao.get(cartLineId);

			Product product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();

			// check if that much quality is available or not
			if (product.getQuantity() < count) {
				return "result=unavailable";
			}
    
			// update the cart Line
			cartLine.setProductCount(count);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice() * count);
			cartLineDao.update(cartLine);
			
			// update the cart
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
			cartLineDao.updateCart(cart);
			return "result=update";
		}
	

	public String deleteCartLine(int cartLineId) {

	
		CartLine cartLine = cartLineDao.get(cartLineId);

		
			// deduct the cart
		    // update the cart
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() - 1);
			cartLineDao.updateCart(cart);

			// remove the cartLine
			cartLineDao.delete(cartLine);
			return "result=deleted";


	}

	public String addCartLine(int productId) {
		Cart cart = this.getCart();
		String response = null;
    CartLine cartLine = cartLineDao.getByCartAndProduct(cart.getId(), productId);

		if (cartLine == null) {
			// added a new cartLine if a new product is getting added
			cartLine = new CartLine();
			Product product = productDao.get(productId);
            // transfer the product details to cartline
			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setProductCount(1);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice());
			
            // insert a new cartLine
			cartLineDao.add(cartLine);
			
			// update the cart
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() + 1);
			cartLineDao.updateCart(cart);

			response = "result=added";
		} else {
			// ceck if the cartLine has been reached reached to maximum count

			if (cartLine.getProductCount() < 3) {
				// call the manageCartLine method to increase the count
              response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
			} 
			else {
				response = "result=maximum";
			}
		}

		return response;
	}

	public String validateCartLine() {
		Cart cart = this.getCart();
		List<CartLine> cartLines = cartLineDao.list(cart.getId());
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "result=success";
		boolean changed = false;
		Product product = null;
		for (CartLine cartLine : cartLines) {
			product = cartLine.getProduct();
			changed = false;
			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			if ((!product.isActive() && product.getQuantity() == 0) && cartLine.isAvailable()) {
				cartLine.setAvailable(false);
				changed = true;
			}
			// check if the cartLine is not available
			// check whether the product is active and has at least one quantity available
			if ((product.isActive() && product.getQuantity() > 0) && !(cartLine.isAvailable())) {
				cartLine.setAvailable(true);
				changed = true;
			}

			// check if the buying price of product has been changed
			if (cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// set the buying price to the new price
				cartLine.setBuyingPrice(product.getUnitPrice());
				// calculate and set the new total
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;
			}

			// check if that much quantity of product is available or not
			if (cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;

			}

			// changes has happened
			if (changed) {
				// update the cartLine
				cartLineDao.update(cartLine);
				// set the result as modified
				response = "result=modified";
			}

			grandTotal += cartLine.getTotal();
			lineCount++;
		}

 		cart.setCartLines(lineCount++);
		cart.setGrandTotal(grandTotal);
		cartLineDao.updateCart(cart);

		return response;
	}
}
