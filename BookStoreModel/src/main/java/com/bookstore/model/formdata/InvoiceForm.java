package com.bookstore.model.formdata;

import com.bookstore.model.entities.Invoice;
import com.bookstore.model.enumerate.InvoiceStatus;

public class InvoiceForm {
	
	private Integer id;

	private InvoiceStatus status;

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static InvoiceForm getStatusFromEntity(Invoice invoice) {
		InvoiceForm invoiceForm = new InvoiceForm();
		invoiceForm.setId(invoice.getId());
		invoiceForm.setStatus(invoice.getStatus());
		return invoiceForm;
	}
	
	public Invoice setNewInvoiceStatus(Invoice invoice) {
		invoice.setStatus(this.status);
		return invoice;
	}
}
