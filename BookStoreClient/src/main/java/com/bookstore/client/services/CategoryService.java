package com.bookstore.client.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.client.repository.CategoryReponsitory;
import com.bookstore.model.entities.Category;

@Service
public class CategoryService {
	@Autowired
	public CategoryReponsitory categoryReponsitory;
	
	public List<Category> getAllCategory(){
		return categoryReponsitory.findAll();
	}
	
	public List<Category> getRootCategory() {
		return categoryReponsitory.getRootCategory();
	}
	
	public Category getById(Integer id) {
		return categoryReponsitory.getById(id);
	}
	
	public List<Category> getListParent(Category category){
		List<Category> listParent = new ArrayList<Category>();
		if(category != null) {
			Category parent =  category.getParent();
			while(parent != null)
			{
				listParent.add(0, parent);
				parent = parent.getParent();
			}
			listParent.add(category);
		}
		return listParent;
	}
}
