package com.bookstore.client.services;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.client.helper.PasswordManager;
import com.bookstore.client.repository.CustomerRepository;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.enumerate.AuthProvider;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void saveCustomer(Customer c) {
		Pattern bcryPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");
		
		if (!bcryPattern.matcher(c.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(c.getPassword());
			c.setPassword(encodePassword);
		}
		
		
		customerRepository.save(c);
	}
	
	public Customer getCustomerByPhone(String phoneNumber) {
		return customerRepository.getByPhoneNumber(phoneNumber);
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepository.getCustomerByEmail(email);
	}
	
	public void registerNewCustomer(String email, String name, AuthProvider provider ) {
		Date createDate = new Date();
		
		Customer newCustomer = new Customer();
		
		newCustomer.setEmail(email);
		newCustomer.setFirstName(name);
		newCustomer.setEmailVerified(false);
		newCustomer.setCreateDate(createDate);
		newCustomer.setLastLogin(createDate);
		newCustomer.setAuthProvider(provider);
		newCustomer.setEnabled(true);
		
		
		customerRepository.save(newCustomer);
	}
	
	public void updateCustomer(Customer customer, String name ,AuthProvider authProvider) {
		Date loginDate = new Date();
		
//		customer.setAddress(null);
		customer.setFirstName(name);
		//customer.setLastName(lastname);
		customer.setLastLogin(loginDate);
		
		customerRepository.save(customer);
	}
	
	//public void updateCustomer
}
