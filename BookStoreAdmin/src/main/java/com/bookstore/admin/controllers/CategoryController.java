package com.bookstore.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.services.CategoryService;
import com.bookstore.admin.storage.StorageService;
import com.bookstore.model.entities.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	private final StorageService storageService;
	
	@Autowired
	public CategoryController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/category")
	public String showCategoryListView(Model model) {
		
		List<Category> listCategory = categoryService.getAllCategory();
		model.addAttribute("listCategory", listCategory);
		
		return "categories";
	}
	
	@RequestMapping("/delete_category/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {
		categoryService.deleteCategoryById(id);
		
		return "redirect:/category";
	}
	
	@GetMapping("/create_category")
	public String showCreateCategoryView(Model model) {
		Category category = new Category();
		List<Category> listCategory = categoryService.getAllCategory();
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("category", category);
		return "create_category";
	}
	
	@RequestMapping(value = "/create_category", method = RequestMethod.POST)
	public String createNewCategory(@Valid Category category, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			List<Category> listCategory = categoryService.getAllCategory();
			model.addAttribute("listCategory", listCategory);
			return "/create_category";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		category.setEnabled(true);
		categoryService.saveCategory(category);
		
		if(!multipartFile.isEmpty()) {
			category.setPhoto("category-photosslash" + category.getId() + "slash" + fileName);
			String uploadDir = "category-photos/" + category.getId();
			storageService.store(uploadDir, multipartFile);
			categoryService.saveCategory(category);
		}
		return "redirect:/category";
	}
	
	@GetMapping("/edit_category/{id}")
	public String showEditCategoryView(@PathVariable("id") Integer id,Model model) {
		Category category = categoryService.getCategoryById(id);
		List<Category> listCategory = categoryService.getAllCategory();
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("category", category);
		return "edit_category";
	}
	
	@RequestMapping(value = "/edit_category", method = RequestMethod.POST)
	public String editCategory(@Valid Category category, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			List<Category> listCategory = categoryService.getAllCategory();
			model.addAttribute("listCategory", listCategory);
			return "/edit_category";
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			category.setPhoto("category-photosslash" + category.getId() + "slash" + fileName);
			String uploadDir = "category-photos/" + category.getId();
			storageService.store(uploadDir, multipartFile);
		}
		category.setEnabled(true);
		categoryService.saveCategory(category);

		return "redirect:/category";
	}
	
	@GetMapping(value = "/search_category")
	public String searchCategory(Model model, @Param("name") String name) {
		List<Category> listCategory = categoryService.fullTextSearchCategoryByName(name);
		model.addAttribute("listCategory", listCategory);
		return "search_category";
	}
}
