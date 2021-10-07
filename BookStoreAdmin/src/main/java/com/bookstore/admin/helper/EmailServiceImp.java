package com.bookstore.admin.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

public class EmailServiceImp {

	private static final String UPDATE_INVOICE_STATUS_EMAIL = "email/invoice-status";
	private static final String EMAIL_SENDER = "longnguyen25089@gmail.com";
	private static final String INVOICE_STATUS = "Xac nhan don hang";

	
	public static void sendInvoiceStatusEmail(JavaMailSender mailSender, TemplateEngine htmlTemplateEngine, 
			final Invoice invoice, final List<InvoiceDetail> invoiceDetail, final Locale locale) throws MessagingException {
		
		final Context ctx = new Context(locale);
		
		final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		String confirmDate = dateFormat.format(new Date());
//		
//		ctx.setVariable("name", recipientName);
//		ctx.setVariable("subscriptionDate", new Date());
//		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
		
		ctx.setVariable("invoice", invoice);
		ctx.setVariable("invoiceDetail", invoiceDetail);
		ctx.setVariable("confirmDate", confirmDate);
		
		final MimeMessage mineMessage = mailSender.createMimeMessage();
		final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mineMessage, "UTF-8");
		
		mimeMessageHelper.setSubject(INVOICE_STATUS + " #" + invoice.getCode());
		mimeMessageHelper.setFrom(EMAIL_SENDER);
		mimeMessageHelper.setTo(invoice.getCustomerInvoice().getEmail());
		
		final String htmlContent = htmlTemplateEngine.process(UPDATE_INVOICE_STATUS_EMAIL, ctx);
		mimeMessageHelper.setText(htmlContent, true);
		
		mailSender.send(mineMessage);
	}
}
