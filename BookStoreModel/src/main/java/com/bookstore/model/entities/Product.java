package com.bookstore.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bookstore.model.formdata.ProductData;

@Entity
@Table(name = "products")
public class Product  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5016113275846964575L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	@Size(min = 1, message = "Please enter product name")
	private String name;
	
	@Column(name = "code")
	@Size(min = 3, max = 10, message = "Please enter product code")
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@NotNull(message = "Please select product's category")
	private Category category;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<InvoiceDetail> invoiceDetail = new HashSet<>();
	
	@Column(name = "status", unique = false, nullable = true, length = 64)
	private String status;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "quantity")
	@NotNull(message = "Please enter product quantity")
	private Integer quantity;
	
	@Column(name = "price")
	@Min(value = 0,message = "Must greater than 0")
	private Float price;
	
	@Column(name = "sale_price")
	@Min(value = 0,message = "Must greater than 0")
	@Min(value = 0)
	private Float salePrice;
	
	@Column(name = "enabled")
	private Boolean enabled;

	public Float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<InvoiceDetail> getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(Set<InvoiceDetail> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public String getStatus() {
		if(this.quantity == 0) {
			status = "Out of Stock";
		} else {
			status = "In Stock";
		}
		return status;
	}

	public void setStatus(String status) {
		status = getStatus();
		this.status = status;
	}

	@Transient
	public void updateFormData(ProductData productData) {
		this.name = productData.getName();
		this.code = productData.getCode();
		this.description = productData.getDescription();
//		this.category = productData.getCategory();
		this.photo = productData.getPhoto();
		this.quantity = productData.getQuantity();
		this.price = productData.getPrice();
		this.salePrice = productData.getSalePrice();
	}
	
	@Transient
	public String getPhotoPath() {
		if(photo == null || photo.equals("")) {
			return "../images/book.jpeg";
		}
		if(id != null & (photo != null || photo.equals(""))) {
			return "/files/" + photo;
		}

		return null;
	}
}
