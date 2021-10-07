package com.bookstore.client.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bookstore.client.security.CustomerOAuth2User;
import com.bookstore.client.services.CustomerService;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.enumerate.AuthProvider;

@Component
public class OnAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private CustomerService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("OnAuthenticationSuccessHandler::" + authentication.getName());
		
		CustomerOAuth2User oAuth2User = (CustomerOAuth2User) authentication.getPrincipal();
		
		String email = oAuth2User.getEmail();
		String name = oAuth2User.getName();
		String servletPath = request.getServletPath();
		
		AuthProvider provider = AuthProvider.BASIC;
		
		if(servletPath.contains("google")) {
			provider = AuthProvider.GOOGLE;
		} else if(servletPath.contains("facebook")) {
			provider = AuthProvider.FACEBOOK;
		}
		
		Customer customer = customerService.getCustomerByEmail(email);
		
		if(customer == null) {
			customerService.registerNewCustomer(email, name, provider);
		} else {
			customerService.updateCustomer(customer, name, provider);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
