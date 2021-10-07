package com.bookstore.admin.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordManager {

	public static String getBCrypPassword(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(rawPassword);
		
		return encoded;
	}
	
	public static Boolean checkPassword(String newPassword, String oldPassword) {
		
		Boolean checked = false;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		checked = encoder.matches(newPassword, oldPassword);
		
		return checked;
	}
}
