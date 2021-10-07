 package com.bookstore.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.client.services.CategoryService;
import com.bookstore.client.services.ProductService;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"/productlist"})
	public String showProductView(Model model) {	
//		System.out.println("sp:*************************************************** " );
//
//		List<Product> products = productService.getAllProduct();
//		model.addAttribute("products", products);
		return showProductPageView(model, 1);
//		return "product-list";
		

	}
	
	@RequestMapping(value = {"/productlist/{pageNum}"})
	public String showProductPageView(Model model, @PathVariable(name = "pageNum") int pageNum) {
		
		Page<Product> pageProduct = productService.getProductsWithPage(pageNum);
		List<Product> products = pageProduct.getContent();
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endCount = startCount + ProductService.PAGE_SIZE - 1;
		
		if( endCount > pageProduct.getTotalElements()) {
			endCount = pageProduct.getTotalElements();
		}
//		for(Product product : products) {
//			System.out.println("Sp:" + product.getCode());
//		}
		
		
		model.addAttribute("products", products);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		return "product";
	}
	
	@GetMapping(value = {"/searchproducts"})
	public String showSearchProduct(Model model, @Param(value = "value") String value) {
		
//		List<Product> listProduct = productService.getProductByName(value);
		
		List<Product> listProduct = productService.fullTextSearchProductByName(value);
		
		System.out.println("Danh sach san pham sau khi search: " + listProduct.size());
		
		
		System.out.println(value);
		
		
		model.addAttribute("products", listProduct);
		
		return "search_product";
	}
	
	@GetMapping(value = {"/categoryproducts"})
	public String showCategoryProduct(Model model) {
		List<Category> listCategory = categoryService.getRootCategory();
		model.addAttribute("categories", listCategory);
		
		
		return "category";
	}
	
	@GetMapping(value = {"/categoryproducts/{id}"} )
	public String showCategoryProductID(Model model , @PathVariable ("id") Integer id) {
		Category category = categoryService.getById(id);
		
		List<Category> listcategory =  new ArrayList<>();
		listcategory.addAll(category.getSubCategory());
		
		List<Category> listParent = categoryService.getListParent(category);
		model.addAttribute("parents", listParent);
		model.addAttribute("categories", listcategory);
		
		
		List<Product> listProduct = productService.getProductCategory(category);
		model.addAttribute("products", listProduct);
		for(Product list : listProduct) {
			System.out.println("product: " + list);
		}
		
		return "category";
	}
	
	@RequestMapping(value = "/productdetail/{id}")
	public String viewProduct(Model model,  @PathVariable ("id") Integer id) {
		Product product = productService.getById(id);
		
//		List<Product> listProducts =  new ArrayList<>();
//		listProducts.addAll(product.getId());
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);

		List<Product> listProduct = productService.getById(product);
		model.addAttribute("products", listProduct);
		
		return "product-detail";
	}

}
