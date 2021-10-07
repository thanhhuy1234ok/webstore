package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE c.email = :email")
	public Customer getCustomerByEmail(@Param("email") String email);
	
	@Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
	public Customer getCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	//search a customer
	@Query(value = "SELECT * FROM customers WHERE email like %?1%", nativeQuery = true)
	public List<Customer> getListSearchCustomerByEmail(@Param("email") String email);
	
	//full text search customer
	@Query(value = "SELECT * FROM customers WHERE MATCH(email) against (?1)", nativeQuery = true)
	public List<Customer> fullTextSearchCustomerByEmail(String email);
}
