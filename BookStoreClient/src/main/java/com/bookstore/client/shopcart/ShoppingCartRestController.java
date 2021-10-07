package com.bookstore.client.shopcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ShoppingCartRestController {

	@PostMapping("/restapi/shopcart_update/{code}/{qty}")
	public String shopcartUpdateQuantity(HttpServletRequest request, @PathVariable(value = "code") String code, @PathVariable(value = "qty") Integer quantity) {
		
		System.out.println("shopcartUpdateQuantity: " + code + " --> " + quantity);
		
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
		
		boolean productFound = false;
		
		for (CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
			if (cartLineInfo.getProduct().getCode().equals(code)) {
				cartLineInfo.setQuantity(quantity);
				productFound = true;
			}
		}
		
		if (productFound) {
			return "Updated!";
		}
		return "Product Not Found";
	}
	
	@PostMapping("restapi/shopcart_delete/{code}")
	public String shopcartDelete(HttpServletRequest request, @PathVariable(value = "code") String code) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
		
		boolean productFound = false;
		
		List<CartLineInfo> cartLineInfos = cartInfo.getCartLines();
		for (CartLineInfo cartLineInfo : cartLineInfos) {
			if(cartLineInfo.getProduct().getCode().equals(code)) {
				cartLineInfos.remove(cartLineInfo);
				productFound = true;
				break;
			}
		}
		
		if (productFound) {
			return "Removed";
		}
		return "Product Not Found";
	}
}
