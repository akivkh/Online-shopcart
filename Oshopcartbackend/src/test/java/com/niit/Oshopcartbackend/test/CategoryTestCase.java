package com.niit.Oshopcartbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.Oshopcartbackend.Dao.CategoryDao;
import com.niit.Oshopcartbackend.model.Category;

public class CategoryTestCase {

	private static AnnotationConfigApplicationContext context;

	private static CategoryDao categoryDao;

	private static Category category;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.Oshopcartbackend");
		context.refresh();
		categoryDao = (CategoryDao) context.getBean("categoryDao");
	}
	
/*	@Test
	public void testAddCatgeory() {
		category=new Category();
		category.setName("Television");
		category.setDescription("This is some description for television");
		category.setImageURL("CAT_3.png");
		
		assertEquals("successfully added a category inside table",true,categoryDao.add(category));
	}*/
	
	/*@Test
	public void testGetCategory() {
		category=categoryDao.get(3);
		assertEquals("successfully fetched a single category from the table","Television",category.getName());
	}*/
	
	/*@Test
	public void testUpdateCategory() {
		category=categoryDao.get(3);
		
		category.setName("TV");
		assertEquals("successfully updated a single category in the table",true,categoryDao.update(category));
	}*/
	/*
	@Test
	public void testDeleteCategory() {
		category=categoryDao.get(3);
		
		assertEquals("successfully deleted a single category from the table",true,categoryDao.delete(category));
	}*/
	/*@Test
	public void testListCategory() {
					
		assertEquals("Successfully fetched the list of categories from the table!",3,categoryDao.list().size());
		
		
	}*/
	
	@Test
	public void testCRUDCategory() {
		
		//add operation
		category=new Category();
		category.setName("Television");
		category.setDescription("This is some description for television");
	
		
		assertEquals("successfully added a category inside table",true,categoryDao.add(category));
	
		category=new Category();
		category.setName("Mobile");
		category.setDescription("This is some description for mobile");
		
		
		assertEquals("successfully added a category inside table",true,categoryDao.add(category));

	
	//fetching and updating the category
	
	category=categoryDao.get(2);
	
	category.setName("TV");
	assertEquals("successfully updated a single category in the table",true,categoryDao.update(category));
	
	//delele the category
	assertEquals("successfully deleted a single category from the table",true,categoryDao.delete(category));
	
	//fetching the list
	assertEquals("Successfully fetched the list of categories from the table!",1,categoryDao.list().size());
}
}