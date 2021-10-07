package com.bookstore.admin.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.services.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@GetMapping("api/user")
	public String checkUserNameExist(@RequestParam(value = "username") String username) {
		return userService.checkUsernameExist(username)?"This username has been taken!":"";
	}
	
	@GetMapping("api/identity")
	public String checkIdentityNumberExist(@RequestParam(value = "identityNumber") String identity) {
		return userService.checkIdentityNumberExist(identity)?"This ID number has been taken!":"";
	}
	
	@GetMapping("api/phone")
	public String checkPhoneNumberExist(@RequestParam(value = "phoneNumber") String phoneNumber) {
		return userService.checkPhoneNumberExist(phoneNumber)?"This phone number has been taken!":"";
	}
	
	@GetMapping("api/user/identity")
	public String chechUserIdentityNumber(@RequestParam(value = "identityNumber") String identityNumber, @RequestParam(value = "userId") Integer userId) {
		return userService.checkUserIdentityNumberExist(identityNumber, userId)?"This ID number has been taken!":"";
	}
	
	@GetMapping("api/user/phone")
	public String chechUserPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam(value = "userId") Integer userId) {
		return userService.checkUserPhoneNumberExist(phoneNumber, userId)?"This phone number has been taken!":"";
	}
}
