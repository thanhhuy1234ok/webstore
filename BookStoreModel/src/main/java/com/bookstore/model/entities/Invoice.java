package com.bookstore.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bookstore.model.enumerate.InvoiceStatus;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1105156889207655108L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customerInvoice;
	
	@OneToMany(mappedBy = "invoiceDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<InvoiceDetail> details = new HashSet<>();
	
	@Column(name = "total_payable")
	private Double totalPayable;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(Customer customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

	public Double getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(Double totalPayable) {
		this.totalPayable = totalPayable;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}

	public Set<InvoiceDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<InvoiceDetail> details) {
		this.details = details;
	}
	
	
}
