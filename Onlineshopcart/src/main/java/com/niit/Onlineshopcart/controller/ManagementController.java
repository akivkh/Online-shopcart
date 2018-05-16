package com.niit.Onlineshopcart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.niit.Onlineshopcart.util.FileUploadUtility;
import com.niit.Onlineshopcart.validator.ProductValidator;
import com.niit.Oshopcartbackend.Dao.CategoryDao;
import com.niit.Oshopcartbackend.Dao.ProductDao;
import com.niit.Oshopcartbackend.model.Category;
import com.niit.Oshopcartbackend.model.Product;

@Controller
@RequestMapping("/manage")

public class ManagementController {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ProductDao productDao;

	// logger for debuging purpose
	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name = "operation", required = false) String operation) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");

		Product nProduct = new Product(); // n stand for new product

		// set few of the field
		nProduct.setSupplierId(1);
		nProduct.setActive(true);

		mv.addObject("product", nProduct);

		if (operation != null) {
			if (operation.equals("product")) {
				mv.addObject("message", "Product submitted Successfully");
			}
			else if(operation.equals("category")) {
				mv.addObject("message", "Category submitted Successfully");
			}
		}
		return mv;

	}
	
	@RequestMapping(value="/{id}/product",method =RequestMethod.GET)
	public ModelAndView showEditProduct(@PathVariable int id) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");
        
		// fetch the product from database
		Product nProduct = productDao.get(id) ;
	    //set the product fetch from database
		mv.addObject("product", nProduct);
		return mv;

	}

	// handling product submission
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct,BindingResult results, Model model, 
			HttpServletRequest request) { // m stand for Modify product
		
		
		
		//handle image validation for new products
		if(mProduct.getId()==0) {
		 new ProductValidator().validate(mProduct,results);
		}
		else {
			  if(!mProduct.getFile().getOriginalFilename().equals("")) {
				  new ProductValidator().validate(mProduct,results);
			  }
			
		}
		
		//check if there are any error
		if(results.hasErrors()) {
			
			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("message", "Validation Failed for Product Submission!");
			return "page";
		}
		
		
		logger.info(mProduct.toString());
		// create a product if id is 0
		if(mProduct.getId()==0) {
		    productDao.add(mProduct);
		}
		else {
			// update the product if id is not 0
		    productDao.update(mProduct);
		}
		
		if(!mProduct.getFile().getOriginalFilename().equals("")) {
		    FileUploadUtility.uploadFile(request, mProduct.getFile(), mProduct.getCode());	 //request object is to fetch Real path
			
		}

		return "redirect:/manage/products?operation=product"; // using query string to display a message inside bootsrap
																// component
	}
	
	@RequestMapping(value="/product/{id}/activation", method=RequestMethod.POST)
	@ResponseBody
	public String handlerProductActivation(@PathVariable int id) {
		//is going to fetch the product from the database
		Product product= productDao.get(id);
		boolean isActive =product.isActive();
		
		//activating or deactivating based on the value of active field
		product.setActive(!product.isActive());
		
		//updating the product
		productDao.update(product);
		
		return (isActive)? 
				"You have Successfully deactivated the product with id " +product.getId()
		         :"You have Successfully activated the product with id " +product.getId();
	}
	
	// to handle category submission
	@RequestMapping(value="/category" , method=RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category) {
	 
		// add the new category
		categoryDao.add(category);
		return "redirect:/manage/products?operation=category";
		
	}
	
	// returning categories for the all request mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDao.list();

	}
	
	@ModelAttribute("category")
     public Category getCategory() {
		return new Category();
	}
}