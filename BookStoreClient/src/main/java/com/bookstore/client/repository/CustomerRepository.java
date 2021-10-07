package com.bookstore.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.email = :email")
	public Customer getCustomerByEmail(@Param("email") String email);
	
	@Query("SELECT c from Customer c WHERE c.phoneNumber = :phoneNumber")
	public Customer getByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
