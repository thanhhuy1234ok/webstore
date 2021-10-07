package com.bookstore.client.helper;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bookstore.model.entities.Customer;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;

public class EmailServiceImpl {

	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email/email-simple";
	private static final String UPDATE_INVOICE_STATUS_EMAIL = "email/invoice-status";
	private static final String EMAIL_SENDER = "nguyenvothanhhuy2002@gmail.com";
	private static final String INVOICE_STATUS = "Xac nhan don hang";
	

    
	  
	public static void sendSimpleMessage(JavaMailSender emailSender, String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("nguyenvothanhhuy2002@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public static void sendHTMLEmail(JavaMailSender mailSender, String from, String to, String subject,
			String message) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText("<h1>BOOKSTORE</h1><BR>" + message);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
    public static void sendSimpleMail( JavaMailSender mailSender, TemplateEngine htmlTemplateEngine,
            final Customer customer, final String recipientEmail, final Locale locale)
            throws MessagingException {

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
//            ctx.setVariable("name", recipientName);
//            ctx.setVariable("subscriptionDate", new Date());
//            ctx.setVariable("hobbies", Arrays.asList("Tiểu thuyết", "Sách khoa học", "Sách kinh doanh"));
            ctx.setVariable("customer", customer);

            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Xác nhận email");
            message.setFrom("thymeleaf@example.com");
            message.setTo(recipientEmail);

            // Create the HTML body using Thymeleaf
            final String htmlContent = htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
           mailSender.send(mimeMessage);
        }
    
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
