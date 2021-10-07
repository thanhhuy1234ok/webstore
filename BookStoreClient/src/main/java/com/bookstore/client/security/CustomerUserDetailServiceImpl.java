package com.bookstore.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookstore.client.repository.CustomerRepository;
import com.bookstore.model.entities.Customer;

public class CustomerUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepos;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepos.getCustomerByEmail(username);
		
		if (customer == null) {
			throw new UsernameNotFoundException("email khong ton tai");
		}
		
		return new CustomerUserDetails(customer);
	}

}
