package com.niit.Oshopcartbackend.Dao;

import java.util.List;

import com.niit.Oshopcartbackend.model.Category;

public interface CategoryDao {

	List<Category> list();
	Category get(int id);
}
