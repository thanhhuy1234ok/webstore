package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.CategoryRepository;
import com.bookstore.model.entities.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	public void deleteCategoryById(Integer id) {
		categoryRepository.deleteById(id);
	}
	
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public Category getCategoryById(Integer id) {
		Category category = categoryRepository.getById(id);
		return category;
	}
	
	public Category getCategoryByName(String name) {
		Category category = categoryRepository.getCategoryByName(name);
		return category;
	}
	
	public Category getCategoryByCode(String code) {
		return categoryRepository.getCategoryByCode(code);
	}
	
	public List<Category> fullTextSearchCategoryByName(String name){
		return categoryRepository.fullTextSearchCategoryByName(name);
	}
	
	public Boolean checkNewCategoryName(String name) {
		Category category = getCategoryByName(name);
		if(category != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkNewCategoryCode(String code) {
		Category category = getCategoryByCode(code);
		if(category != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkCategoryName(String name, Integer id) {
		Category category = getCategoryByName(name);
		if(category != null && category.getId() != id) {
			return true;
		}
		return false;
	}
}
