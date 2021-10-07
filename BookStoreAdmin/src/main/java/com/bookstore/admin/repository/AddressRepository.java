package com.bookstore.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Address;
import com.bookstore.model.entities.Customer;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

	@Query("SELECT a FROM Address a WHERE a.customer = :customer")
	public Address getAddressByCustomer(@Param("customer") Customer customer);
}
