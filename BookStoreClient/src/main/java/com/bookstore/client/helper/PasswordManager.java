package com.bookstore.client.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordManager extends BCryptPasswordEncoder {

	public static String getBCrypPassword(String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(rawPassword);
		
		return encoded;
	}
}
