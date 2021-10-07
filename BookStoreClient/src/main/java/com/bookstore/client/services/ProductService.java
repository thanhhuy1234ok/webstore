package com.bookstore.client.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.client.repository.ProductReponsitory;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Service
public class ProductService {
	
	public static int PAGE_SIZE = 9;
	
	@Autowired
	public ProductReponsitory productReponsitory;
	
	public List<Product> getAllProduct(){
		return productReponsitory.findAll();
	}
	
	public Product getById(Integer id) {
		return productReponsitory.getById(id);
	}
	
	public Page<Product> getProductsWithPage(int pagenum){
		Pageable pageable;
		
		if (pagenum >= 1) {
			pageable = PageRequest.of(pagenum - 1, ProductService.PAGE_SIZE);
		}else {
			pageable = PageRequest.of(0, ProductService.PAGE_SIZE);
		}
		
		return productReponsitory.findAll(pageable);
	}
	
	public List<Product> getProductByName(String name){
		return productReponsitory.getProductByName(name);
	}
	
	public List<Product> fullTextSearchProductByName(String name){
		return productReponsitory.fullTextSearchProductByName(name);
	}
	
	public List<Product> getProductCategory(Category category) {
		List<Product> listProducts = productReponsitory.getProductByCategoryId(category.getId());
		for(Product product : listProducts)
		{
			System.out.println("1: " + product);
		}
		return listProducts;
	}
	
	public Product getByCode(String code) {
		return productReponsitory.getProductByCode(code);
	}

	public List<Product> getById(Product productId) {
		List<Product> listProducts = productReponsitory.getProductById(productId.getId());
		return listProducts;
	}
}
