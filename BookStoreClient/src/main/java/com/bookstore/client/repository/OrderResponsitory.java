package com.bookstore.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

@Repository
public interface OrderResponsitory extends JpaRepository<Invoice, Integer> {
	@Query("SELECT i FROM Invoice i WHERE i.code = :code")
	public Invoice getInvoiceByCode(@Param("code") String code);
	
	@Query("SELECT i FROM Invoice i WHERE i.customerInvoice = :customerInvoice")
	public List<Invoice> getInvoiceByCustomerInfo(@Param("customerInvoice") Customer customer);
	
	@Query("SELECT i FROM InvoiceDetail i WHERE i.invoiceDetail = :invoiceDetail")
	public List<InvoiceDetail> getInvoiceDetail(@Param("invoiceDetail") Invoice invoice);
}
