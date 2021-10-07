package com.bookstore.client.shopcart;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import com.bookstore.client.helper.EmailServiceImpl;
import com.bookstore.client.services.OrderServiecs;
import com.bookstore.client.services.ProductService;
import com.bookstore.model.entities.Invoice;
import com.bookstore.model.entities.InvoiceDetail;
import com.bookstore.model.entities.Product;
import com.bookstore.model.enumerate.InvoiceStatus;
import com.bookstore.model.formdata.InvoiceForm;

@Controller
public class ShoppingCartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderServiecs orderService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine htmlTemplateEngine;

	private InvoiceForm invoiceForm = new InvoiceForm();

	@RequestMapping("/addtocart")
	public String byProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {

		Product product = productService.getByCode(code);

		if (product != null) {
			CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
			cartInfo.addProduct(product, 1);
		}

		return "redirect:/shopping_cart";
	}

	@RequestMapping("/shopping_cart")
	public String showCartView(HttpServletRequest request, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());

		return "cart";
	}

	@PostMapping("/update_cart")
	public String updateCart(@ModelAttribute("cartInfo") CartInfo cartForm, HttpServletRequest request, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
		System.out.println("updateCart -> " + cartInfo.getCartLines().size());

		for (int i = 0; i < cartInfo.getCartLines().size(); i++) {
			cartInfo.getCartLines().get(i).setQuantity(cartForm.getCartLines().get(i).getQuantity());
		}
		return "redirect:/shopping_cart";
	}

	@GetMapping("/checkout")
	public String checkoutShoppingCart(HttpServletRequest req, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		} else {
//			Invoice invoice = orderService.getInvoiceById(id);
//
//			InvoiceForm invoiceForm = InvoiceForm.getStatusFromEntity(invoice);
//			
//			List<InvoiceDetail> invoiceDetail = orderService.getInvoiceDetail(invoice);

			CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(req);
			model.addAttribute("orderCode", orderService.saveOrder(cartInfo));

			ShopCartSessionUtil.removeCartInSession(req);

			return "checkout";
		}
	}

	@GetMapping("/deletecart")
	public String removeCart(HttpServletRequest request) {
		ShopCartSessionUtil.removeCartInSession(request);

		return "redirect:/shopping_cart";
	}

}
