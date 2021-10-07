package com.bookstore.admin.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.services.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("api/product")
	public String checkProductNameExist(@RequestParam (value = "name") String name, @RequestParam (value = "id") Integer id) {
		return productService.checkProductExist(name, id)?"This name had been used!":"";
	}
	
	@GetMapping("api/code")
	public String checkCodeExist(@RequestParam(value = "code") String code) {
		return productService.checkCodeExist(code)?"This code has been taken!":"";
	}
	
	@GetMapping("api/name")
	public String checkNameExist(@RequestParam(value = "name") String name) {
		return productService.checkNameExist(name)?"This name has been taken!":"";
	}
}
