package com.bookstore.client.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.bookstore.client.repository.OrderResponsitory;
import com.bookstore.client.security.CustomerUserDetails;
import com.bookstore.client.shopcart.CartInfo;
import com.bookstore.client.shopcart.CartLineInfo;
import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;
import com.bookstore.model.enumerate.InvoiceStatus;

@Service
public class OrderServiecs {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderResponsitory orderResponsitory;
	
	Customer customer;
	
	public Invoice getInvoiceById(Integer id) {
		return orderResponsitory.getById(id);
	}
	
	public void saveInvoice(Invoice invoice) {
		orderResponsitory.save(invoice);
	}
	
	public List<InvoiceDetail> getInvoiceDetail(Invoice invoice){
		return orderResponsitory.getInvoiceDetail(invoice);
	}
	
	public Invoice getInvoiceByCode(String code) {
		return orderResponsitory.getInvoiceByCode(code);
	}
	
	public List<Invoice> getInvoiceByCustomer(Customer customer){
		return orderResponsitory.getInvoiceByCustomerInfo(customer);
	}
	
	
	public String saveOrder(CartInfo cartInfo) {
		Invoice invoice = new Invoice();
		Set<InvoiceDetail> listInvoiceDetail = new HashSet<InvoiceDetail>();
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		CustomerUserDetails customerUserDetails = (CustomerUserDetails) authentication.getPrincipal();
		String email = customerUserDetails.getUsername();
		Customer customer = customerService.getCustomerByEmail(email);
		
		for (CartLineInfo catLineInfo : cartInfo.getCartLines()) {
			InvoiceDetail invoiceDetail = new InvoiceDetail();
			invoiceDetail.setInvoiceDetail(invoice);
			
			invoiceDetail.setItemTotal(catLineInfo.getTotal());
			invoiceDetail.setQuantity(catLineInfo.getQuantity());
			invoiceDetail.setProduct(catLineInfo.getProduct());
			


			listInvoiceDetail.add(invoiceDetail);
			
		}
		
		invoice.setCustomerInvoice(customer);
		invoice.setDetails(listInvoiceDetail);
		invoice.setStatus(InvoiceStatus.NEW);
		invoice.setTotalPayable(cartInfo.totalCartInfo());
		invoice.setCode(System.currentTimeMillis() + "");
		
		orderResponsitory.save(invoice);
		
		return invoice.getCode();	
	}
	

}
