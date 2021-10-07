package com.bookstore.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.identityNumber = :identityNumber")
	public User getUserByIdentityNumber(@Param("identityNumber") String identityNumber);
	
	@Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
	public User getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	@Query(value = "SELECT * FROM users WHERE MATCH(username) against (?1)", nativeQuery = true)
	public User fullTextSearchUsername(String username);
}
