package com.bookstore.admin.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.services.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/api/new_category_name")
	public String checkNewCategoryNameExist(@RequestParam(value = "categoryName") String categoryName) {
		return categoryService.checkNewCategoryName(categoryName)?"This name has been taken!":"";
	}
	
	@GetMapping("/api/new_category_code")
	public String checkNewCategoryCodeExist(@RequestParam(value = "categoryCode") String categoryCode) {
		return categoryService.checkNewCategoryCode(categoryCode)?"This code has been taken!":"";
	}
	
	@GetMapping("/api/category_name")
	public String checkCategoryNameExist(@RequestParam(value = "categoryName") String categoryName, @RequestParam(value = "categoryId") Integer categoryId) {
		return categoryService.checkCategoryName(categoryName, categoryId)?"This name has been taken!":"";
	}
}
