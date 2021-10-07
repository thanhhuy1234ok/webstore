package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.InvoiceDetailRepository;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

@Service
public class InvoiceDetailService {

	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;
	
	public List<InvoiceDetail> getInvoiceDetail(Invoice invoice){
		return invoiceDetailRepository.getInvoiceDetail(invoice);
	}
}
