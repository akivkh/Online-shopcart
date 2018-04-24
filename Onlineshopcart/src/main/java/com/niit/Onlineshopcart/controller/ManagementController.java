package com.niit.Onlineshopcart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	private static Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name = "operation", required = false) String operation) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");

		Product nProduct = new Product(); // n stand for new product

		// set few of the field
		nProduct.setCategoryId(1);
		nProduct.setActive(true);

		mv.addObject("product", nProduct);

		if (operation != null) {
			if (operation.equals("product")) {
				mv.addObject("message", "Product submitted Successfully");
			}
		}
		return mv;

	}

	// handling product submission
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@ModelAttribute("product") Product mProduct) { // m stand for Modify product

		logger.info(mProduct.toString());
		// create a product record
		productDao.add(mProduct);

		return "redirect:/manage/products?opertion=product"; // using query string to display a message inside bootsrap
																// component
	}

	// returning categories for the all request mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDao.list();

	}

}