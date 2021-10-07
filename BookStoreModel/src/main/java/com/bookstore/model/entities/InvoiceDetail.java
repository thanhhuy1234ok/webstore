package com.bookstore.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8340652995103022974L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "item_total")
	private Double itemTotal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Invoice invoiceDetail;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Product product;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}

	public Invoice getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(Invoice invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
