package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	@Query(value = "SELECT * FROM roles WHERE MATCH(role_name) against (?1)", nativeQuery = true)
	public List<Role> fullTextSearchRoleByName(String name);

	@Query("SELECT r FROM Role r WHERE r.name = :name")
	public Role getRoleByName(@Param("name") String name);
}
