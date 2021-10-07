package com.bookstore.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.AddressRepository;
import com.bookstore.model.entities.Address;
import com.bookstore.model.entities.Customer;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public Address getAddressByCustomer(Customer customer) {
		return addressRepository.getAddressByCustomer(customer);
	}
	
	public void saveAddress(Address address) {
		addressRepository.save(address);
	}
	
	public Address getAddressById(Integer id) {
		return addressRepository.getById(id);
	}
	
	public void deleteAddressById(Integer id) {
		addressRepository.deleteById(id);
	}
}
