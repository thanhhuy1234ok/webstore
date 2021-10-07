package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query("SELECT c FROM Category c WHERE c.code = :code")
	public Product getByCode(@Param("code") String code);
	
	@Query("SELECT c FROM Category c WHERE c.name = :name")
	public Category getCategoryByName(@Param("name") String name); 
	
	@Query("SELECT c FROM Category c WHERE c.code = :code")
	public Category getCategoryByCode(@Param("code") String code); 
	
	@Query(value = "SELECT * FROM categories WHERE MATCH(name) against (?1)", nativeQuery = true)
	public List<Category> fullTextSearchCategoryByName(String name);
}
