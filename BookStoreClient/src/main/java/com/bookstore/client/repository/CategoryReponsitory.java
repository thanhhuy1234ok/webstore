package com.bookstore.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.model.entities.Category;

public interface CategoryReponsitory extends JpaRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.parent = null")
	public List<Category> getRootCategory();
	
}
