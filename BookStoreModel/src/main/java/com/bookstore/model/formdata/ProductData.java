package com.bookstore.model.formdata;

import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

public class ProductData {
	
	private String name;
	
	private String code;
	
	private String description;
	
	private Category category;
	
	private String photo;
	
	private Integer quantity;
	
	private Float price;
	
	private Float salePrice;
	
	public static ProductData copyValueFormEntity(Product product) {
		ProductData data = new ProductData();
		
		data.name = product.getName();
		data.code = product.getCode();
		data.description = product.getDescription();
//		data.category = product.getCategory();
		data.photo = product.getPhoto();
		data.quantity = product.getQuantity();
		data.price = product.getPrice();
		data.salePrice = product.getSalePrice();
		
		return data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	
}
