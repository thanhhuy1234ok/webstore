package com.bookstore.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
	
	@Query("SELECT i FROM InvoiceDetail i WHERE i.invoiceDetail = :invoiceDetail")
	public List<InvoiceDetail> getInvoiceDetail(@Param("invoiceDetail") Invoice invoice);

}
