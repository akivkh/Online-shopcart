package com.niit.Onlineshopcart.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niit.Onlineshopcart.exception.ProductNotFoundException;
import com.niit.Oshopcartbackend.Dao.CategoryDao;
import com.niit.Oshopcartbackend.Dao.ProductDao;
import com.niit.Oshopcartbackend.model.Category;
import com.niit.Oshopcartbackend.model.Product;

@Controller
public class PageController {
	
	 private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	
	@RequestMapping(value= {"/","/home","/index"})
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","Home");
		
		logger.info("Inside PageController index method - INFO");
		logger.debug("Inside PageController index method - DEBUG");
		
		//passing the list category
		mv.addObject("categories",categoryDao.list());
		mv.addObject("userClickHome",true);
		return mv;
	}
	@RequestMapping(value= "/about")
	public ModelAndView about() {
		
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;
	}
	@RequestMapping(value= "/contact")
	public ModelAndView contact() {
		
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	/**
	 * Methods to load all Products and based on category
	 * 
	 */
	
	@RequestMapping(value= "/show/all/products")
	public ModelAndView showAllProducts() {
		
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","All Products");
		
		//passing the list category
		mv.addObject("categories",categoryDao.list());
		mv.addObject("userClickAllProducts",true);
		return mv;
}
	@RequestMapping(value= "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		
		ModelAndView mv = new ModelAndView("page");
		
		//categoryDao to fetch single category
		Category category=null;
		category=categoryDao.get(id);
		
		mv.addObject("title",category.getName());
		
		//passing the list category
		mv.addObject("categories",categoryDao.list());
		
		//passing the single category object
		mv.addObject("category",category);
		
		mv.addObject("userClickCategoryProducts",true);
		return mv;
}
	
	/*
	 * Viewing a single Product
	 * 
	 */
	
	@RequestMapping(value="/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		
		ModelAndView mv=new ModelAndView("page");
		
		Product product= productDao.get(id);
		
		if(product == null)throw new ProductNotFoundException();
		
	    //update the view count
		product.setViews(product.getViews() +1);
		productDao.update(product);
		//------------------------------------
		
		mv.addObject("title",product.getName());
		mv.addObject("product",product);
		
		mv.addObject("userClickShowProduct",true);
		
		
		return mv;
	}
	
	
	
	
	
}