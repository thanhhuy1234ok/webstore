package com.bookstore.admin.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.CustomerRepository;
import com.bookstore.model.entities.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static int PAGE_SIZE = 15;
	
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
	}
	
	public void save(Customer customer) {
		Pattern bcryptPasswordPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");  
		
		if(!bcryptPasswordPattern.matcher(customer.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(customer.getPassword());
			
			customer.setPassword(encodePassword);
		}
		
		customerRepository.save(customer);
	}
	
	public void deleteCustomerById(Integer id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		optionalCustomer.ifPresent(pro -> customerRepository.deleteById(id));
	}
	
//	public Boolean checkCustomerExist(Integer id) {
//		Boolean isExist = false;
//		Optional<Customer> optionalCustomer = customerRepository.findById(id);
//		optionalCustomer.ifPresent(pro -> isExist = true);
//	}
	
	public Customer getCustomerById(Integer id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if( optionalCustomer.isPresent()) {
			return customerRepository.getById(id);
		}
		return null;
	}
	
	public List<Customer> getListSearchCustomerByEmail(String email){
		return customerRepository.getListSearchCustomerByEmail(email);
	}
	
	public List<Customer> fullTextSearchCustomerByEmail(String email){
		return customerRepository.fullTextSearchCustomerByEmail(email);
	}
	
	public Customer getCustomerByPhoneNumber(String phoneNumber) {
		return customerRepository.getCustomerByPhoneNumber(phoneNumber);
	}
	
	public Customer getCustomerbyEmail(String email) {
		return customerRepository.getCustomerByEmail(email);
	}
	
	public Page<Customer> getCustomerWithPage(int pageNum){
		Pageable pageable;
		
		if(pageNum > 1) {
			pageable = PageRequest.of(pageNum - 1, CustomerService.PAGE_SIZE);
		} else {
			pageable = PageRequest.of(0, CustomerService.PAGE_SIZE);
		}
		
		return customerRepository.findAll(pageable);
	}
	
	public Boolean checkNewCustomerEmailExist(String email) {
		Customer customer = getCustomerbyEmail(email);
		
		if(customer != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkNewCustomerPhoneNumberExist(String phoneNumber) {
		Customer customer = getCustomerByPhoneNumber(phoneNumber);
		
		if(customer != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkCustomerPhoneExist(String phoneNumber, Integer id) {
		Customer customer = getCustomerByPhoneNumber(phoneNumber);
		
		if(customer != null && customer.getId() != id) {
			return true;
		}
		return false;
	}
}
