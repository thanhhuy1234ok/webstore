package com.bookstore.client.helper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.client.repository.ProductReponsitory;
import com.bookstore.client.shopcart.CartInfo;
import com.bookstore.client.shopcart.CartLineInfo;
import com.bookstore.client.shopcart.ShopCartSessionUtil;
import com.bookstore.model.entities.Product;
import com.bookstore.model.formdata.ProductAutoComplete;

@RestController
public class ProductResCotronller {
	
	@Autowired
	private ProductReponsitory repositoryProduct;

	@GetMapping("api/products")
	public List<Product> greeting(@RequestParam(value = "name", defaultValue = "") String name) {
		List<Product> listProducts = new ArrayList<>();
		if(name.equals("")) {
			listProducts = repositoryProduct.findAll();		
		}
		else
		{
			listProducts = repositoryProduct.fullTextSearchProductByName(name);
		}
		
		return listProducts;
	}
	
	@GetMapping("api/products/autocomplete")
	public List<ProductAutoComplete> productAutoComplete(@RequestParam(value = "name", defaultValue = "") String name){
		List<Product> listProducts = new ArrayList<>();
		List<ProductAutoComplete> listAuto = new ArrayList<>();
		if(!name.equals("")) {			
			listProducts = repositoryProduct.autoCompleteProductByName(name);
		}
		
		for(Product product:listProducts) {
			ProductAutoComplete newProduct = new ProductAutoComplete();
			newProduct.setId(product.getId());
			newProduct.setName(product.getName());
			newProduct.setPhoto("https://cdn.nguyenkimmall.com/images/thumbnails/600/336/detailed/741/10049758-laptop-asus-x415e-i3-1115g4-14-inch-x415ea-ek047t-1.jpg");
			listAuto.add(newProduct);
		}
		return listAuto;
	}
	
//	@PostMapping("/restapi/shopcart_update/{code}/{qty}")
//	public String shopcartUpdateQuantity(HttpServletRequest request, @PathVariable(value = "code") String code, @PathVariable(value = "qty") Integer quantity) {
//		
//		System.out.println("shopcartUpdateQuantity: " + code + " --> " + quantity);
//		
//		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
//		
//		boolean productFound = false;
//		
//		for (CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
//			if (cartLineInfo.getProduct().getCode().equals(code)) {
//				cartLineInfo.setQuantity(quantity);
//				productFound = true;
//			}
//		}
//		
//		if (productFound) {
//			return "Updated!";
//		}
//		return "Product Not Found";
//	}
}
