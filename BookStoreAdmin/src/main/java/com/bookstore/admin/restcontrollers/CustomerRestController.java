package com.bookstore.admin.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.services.CustomerService;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping("/api/customer/new_phone_number")
	public String checkNewCustomerPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
		return customerService.checkNewCustomerPhoneNumberExist(phoneNumber)?"This phone number has been taken!":"";
	}
	
	@GetMapping("/api/customer/new_email")
	public String checkNewCustomerEmail(@RequestParam(value = "email") String email) {
		return customerService.checkNewCustomerEmailExist(email)?"This email has been taken!":"";
	}
	
	@GetMapping("/api/customer/phone_number")
	public String checkCustomerPhoneNumberExist(@RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam(value = "customerId") Integer customerId) {
		return customerService.checkCustomerPhoneExist(phoneNumber, customerId)?"This phone number has been taken!":"";
	}
}
