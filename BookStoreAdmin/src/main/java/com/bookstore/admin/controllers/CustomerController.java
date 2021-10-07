package com.bookstore.admin.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.services.AddressService;
import com.bookstore.admin.services.CustomerService;
import com.bookstore.admin.storage.StorageService;
import com.bookstore.model.entities.Address;
import com.bookstore.model.entities.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressService addressService;
	
	private final StorageService storageService;
	
	@Autowired
	public CustomerController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/customer")
	public String showCustomerListView(Model model) {
		List<Customer> listCustomer = customerService.getAllCustomer();
		model.addAttribute("listCustomer", listCustomer);

		return "redirect:/customer/1";
	}
	
	@RequestMapping(value = "customer/{pageNum}")
	public String showCustomerPageView(Model model, @PathVariable("pageNum") int pageNum) {
		Page<Customer> pageCustomer = customerService.getCustomerWithPage(pageNum);
		List<Customer> listCustomer = pageCustomer.getContent();
		
		long startCount = (pageNum - 1) * CustomerService.PAGE_SIZE + 1;
		long endCount = startCount + CustomerService.PAGE_SIZE - 1;
		
		if(endCount > pageCustomer.getTotalElements()) {
			endCount = pageCustomer.getTotalElements();
		}
		
		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("totalPages", pageCustomer.getTotalPages());
		model.addAttribute("totalCustomer", pageCustomer.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		return "customers";
	}
	
	@RequestMapping("/delete_customer/{id}")
	public String deleteCustomerById(@PathVariable(name = "id") Integer id) {
		
		customerService.deleteCustomerById(id);
		
		return "redirect:/customer/1";
	}
	
	@GetMapping("/create_customer")
	public String showCreateCustomerView(Model model) {
		Customer customer = new Customer();
		Address address = new Address();
		
		model.addAttribute("customer", customer);
		model.addAttribute("address", address);		
		
		return "create_customer";
	}
	
	@RequestMapping(value = "/create_customer", method = RequestMethod.POST)
	public String checkNewCustomer(@Valid Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes, 
			@RequestParam("fileImage") MultipartFile multipartFile, Address address) {
		
		if(bindingResult.hasErrors()) {
			return "/create_customer";
		}

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		Set<Address> newAddress = new HashSet<Address>();

		customerService.save(customer);
		
		if(!address.getStreet().equals("") && !address.getWard().equals("") && !address.getDistrict().equals("") && !address.getCity().equals("")) {
			newAddress.add(address);
			address.setCustomer(customer);
			addressService.saveAddress(address);
		}
		
		if(!multipartFile.isEmpty()) {
			customer.setPhotoUrl("customer-photosslash" + customer.getId() + "slash" + fileName);
			String uploadDir = "customer-photos/" + customer.getId();
			storageService.store(uploadDir, multipartFile);
			customerService.save(customer);
		}
		
		return "redirect:/customer/1";
	}
	
	@GetMapping("/edit_customer/{id}")
	public String showEditCustomerView(@PathVariable(name = "id") Integer id, Model model) {
		
		Customer customer = customerService.getCustomerById(id);
		
		if(customer != null) {
			model.addAttribute("customer", customer);
			return "edit_customer";
		}
		return "redirect:/customer/1";
	}
	
	@RequestMapping(value = "/edit_customer", method = RequestMethod.POST)
	public String checkCustomerInfo(@Valid Customer customer, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
		
		
		if(bindingResult.hasErrors()) {
			return "/edit_customer";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		if(!fileName.equals("")) {
			customer.setPhotoUrl("customer-photosslash" + customer.getId() + "slash" + fileName);
			String uploadDir = "customer-photos/" + customer.getId();
			storageService.store(uploadDir, multipartFile);
			customerService.save(customer);
		}
		
		customerService.save(customer);
		return "redirect:/customer/1";
		
	}
	
	@GetMapping("/edit_address/{id}")
	public String showEditAddressView(@PathVariable(name = "id") Integer id, Model model) {
		
		Address address = addressService.getAddressById(id);
		
		model.addAttribute("address", address);
		
		return "change_address";
	}
	
	@RequestMapping(value = "/edit_address", method = RequestMethod.POST)
	public String changeAddress(Address address, Model model) {
		Customer customer = customerService.getCustomerById(address.getCustomer().getId());
		model.addAttribute("customer", customer);
		addressService.saveAddress(address);
		return "redirect:/edit_customer/" + address.getCustomer().getId();
	}
	
	@GetMapping("/create_address/{customerID}")
	public String showCreateAddressView(@PathVariable(name = "customerID") Integer customerID, Model model) {
		Address address = new Address();
		
		address.setCustomer(customerService.getCustomerById(customerID));
		System.out.println("showCreateAddressView :: " + address.getCustomer().getEmail());
		model.addAttribute("address", address);
		
		return "create_address";
	}
	
	@RequestMapping(value = "/create_address", method = RequestMethod.POST)
	public String createAddress(Address address, Model model) {
		Customer customer = customerService.getCustomerById(address.getCustomer().getId());
		model.addAttribute("customer", customer);
		addressService.saveAddress(address);
		return "redirect:/edit_customer/" + address.getCustomer().getId();
	}
	
	@RequestMapping("/delete_address/{id}")
	public String deleteAddress(@PathVariable(name = "id") Integer id) {
		Customer customer = addressService.getAddressById(id).getCustomer();
		addressService.deleteAddressById(id);

		return "redirect:/edit_customer/" + customer.getId();
	}
	
	@GetMapping(value = "/search_customer")
	public String searchCustomerView(Model model, @Param(value = "email") String email) {
		List<Customer> listCustomer = customerService.getListSearchCustomerByEmail(email);
		System.out.println("searchCustomerView ::" + listCustomer.size());
		model.addAttribute("listCustomer", listCustomer);
		return "search_customer";
	}
}
