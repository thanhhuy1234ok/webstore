package com.bookstore.client.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.client.services.CustomerService;
import com.bookstore.client.storage.StorageService;
import com.bookstore.model.entities.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	private final StorageService storageService;
	
	@Autowired
	public CustomerController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping(value = ("/profile"))
	public String showViewMyAccount(Authentication authentication, Model model ) {
		
		if(authentication ==  null) {
			return "redirect:/account";
		}
		Customer customer = customerService.getCustomerByEmail(authentication.getName());
		
		model.addAttribute("customer", customer);
		return "profile";
	}
	

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String checkNewCustomer(@Valid Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
		if(bindingResult.hasErrors()) {
			return "profile";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
//		if(!fileName.equals("")) {
//			customer.setPhotoUrl(fileName);
//			String uploadDir = AppConstant.CUSTOMER_PHOTO_DIR + "/" + customer.getId();
//			
//			try {
//				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		if(!multipartFile.isEmpty()) {
			customer.setPhotoUrl("customer-photosslash" + customer.getId() + "slash" + fileName);
			String uploadDir = "customer-photos/" + customer.getId();
			storageService.store(uploadDir, multipartFile);
		}
		
		customer.setEnabled(true);
		customerService.saveCustomer(customer);
		
		return "redirect:/profile";
	}
}
