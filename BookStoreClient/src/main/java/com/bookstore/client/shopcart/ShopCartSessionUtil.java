package com.bookstore.client.shopcart;

import javax.servlet.http.HttpServletRequest;

public class ShopCartSessionUtil {

	private static final String SHOPCART = "SHOP_CART";
	
	public static CartInfo getCartInSession(HttpServletRequest request) {
		
		CartInfo cartInfo = (CartInfo) request.getSession().getAttribute(SHOPCART);
		
		if ( cartInfo == null) {
			cartInfo = new CartInfo();
			request.getSession().setAttribute(SHOPCART, cartInfo);
		}
		
		return cartInfo;
	}
	
	public static void removeCartInSession(HttpServletRequest request) {
		request.getSession().removeAttribute(SHOPCART);
		
	}
}
