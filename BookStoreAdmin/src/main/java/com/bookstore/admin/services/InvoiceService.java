package com.bookstore.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.InvoiceRepository;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public static int PAGE_SIZE = 10;
	
	public List<Invoice> getAllInvoice(){
		return invoiceRepository.findAll();
	}
	
	public Invoice getInvoiceById(Integer id) {
		return invoiceRepository.getById(id);
	}
	
	public Invoice getInvoiceByCode(String code) {
		return invoiceRepository.getInvoiceByCode(code);
	}
	
	public List<Invoice> getInvoiceByCustomer(Customer customer){
		return invoiceRepository.getInvoiceByCustomerInfo(customer);
	}
	
	public Page<Invoice> getInvoiceWithPage(int pageNum){
		Pageable pageable;
		
		if(pageNum > 1) {
			pageable = PageRequest.of(pageNum - 1, InvoiceService.PAGE_SIZE);
		} else {
			pageable = PageRequest.of(0, InvoiceService.PAGE_SIZE);
		}
		
		return invoiceRepository.findAll(pageable);
	}
	
	public void saveInvoice(Invoice invoice) {
		invoiceRepository.save(invoice);
	}
}
