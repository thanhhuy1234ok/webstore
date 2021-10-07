package com.bookstore.admin.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.services.CategoryService;
import com.bookstore.admin.services.ProductService;
import com.bookstore.admin.storage.StorageFileNotFoundException;
import com.bookstore.admin.storage.StorageService;
import com.bookstore.model.entities.Category;
import com.bookstore.model.entities.Product;

@Controller
public class AppController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	private final StorageService storageService;
	
	@Autowired
	public AppController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/files/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		filename = filename.replace("slash", "/");
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/login")
	public String showLoginView() {
		System.out.println("showLoginView");
		return "login";
	}

	@RequestMapping("/dashboard")
	public String showDashboardView(Model model) {
		return "dashboard";
	}
	
	@RequestMapping("/")
	public String showHomeView(Model model) {
		System.out.println("showHomeView");
		return "redirect:/profile";
	}
	
	@GetMapping("/product")
	public String showBooksView(Model model) {
		List<Product> listProducts = productService.getAllProducts();
		List<Category> listCategories = categoryService.getAllCategory();
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("listCategories", listCategories);
		return "redirect:/product/1";
	}
	
	@RequestMapping(value = {"/product/{pageNum}"})
	public String showBookPageView(Model model, @PathVariable(name = "pageNum") int pageNum) {
		Page<Product> pageProduct = productService.getProductWithPage(pageNum);
		List<Product> listProducts = pageProduct.getContent();
		List<Category> listCategories = categoryService.getAllCategory();
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endcount = startCount + ProductService.PAGE_SIZE - 1;
		
		if(endcount > pageProduct.getTotalElements()) {
			endcount = pageProduct.getTotalElements();
		}

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endcount);
		model.addAttribute("listCategories", listCategories);
		
		return "products";
	}
	
	@GetMapping("/add_new_product")
	public String showShowAddNewProductView(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("listCategory",categoryService.getAllCategory());
		return "add_new_product";
	}
	
	@RequestMapping(value = "/add_new_product", method = RequestMethod.POST)
	public String createProduct(@Valid Product product, BindingResult bindingResult,@RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listCategory",categoryService.getAllCategory());
			return "add_new_product";
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

//		if(!fileName.equals("")) {
//			product.setPhoto(fileName);
//			String uploadDir = AppConstant.PRODUCT_PHOTO_DIR + "/" + product.getId();
//			System.out.println("product id : " + product.getId());
//			try {
//				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		product.setEnabled(true);
		productService.saveProduct(product);
		if(!multipartFile.isEmpty()) {
			product.setPhoto("product-photosslash" + product.getId() + "slash" + fileName);
			String uploadDir = "product-photos/" + product.getId();
			storageService.store(uploadDir, multipartFile);
			productService.saveProduct(product);
		}
		return "redirect:/product/1";
	}
	
	@GetMapping("/edit_product/{code}")
	public String showEditProductView(@PathVariable(name = "code") String code, Model model) {
		
		Product product = productService.getProductByCode(code);
		
		if(product == null) {
			return "redirect:/product/1";
		}
		
		model.addAttribute("product", product);
		model.addAttribute("listCategory", categoryService.getAllCategory());
		
		return "edit_product";
	}
	
	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String saveProduct(@Valid Product product, BindingResult bindingResult, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listCategory",categoryService.getAllCategory());
			return "/edit_product";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!multipartFile.isEmpty()) {
			product.setPhoto("product-photosslash" + product.getId() + "slash" + fileName);
			String uploadDir = "product-photos/" + product.getId();
			storageService.store(uploadDir, multipartFile);
		}
		
		product.setEnabled(true);
		productService.saveProduct(product);

		return "redirect:/product/1";
	}
	
	@GetMapping(value = "/search_product")
	public String searchProductView(Model model, @Param("name") String name, @RequestParam(name = "selectedCategory") String categorySelected) {
		
		List<Product> listProducts = new ArrayList<Product>();
		List<Category> listCategories = categoryService.getAllCategory();
		Category category = categoryService.getCategoryByName(categorySelected);
		System.out.println("searchProductView :: " + name);System.out.println("searchProductView :: " + categorySelected);
		if(categorySelected.equals("All") && name.equals("")) {
			return "redirect:/product/1";
		} else if (name.equals("") && !categorySelected.equals("All")) {
			listProducts = productService.getListProductByCategory(category);
		} else if (!name.equals("") && categorySelected.equals("All")) {
			listProducts = productService.fullTextSearchProduct(name);
		} else {
			listProducts = productService.searchProductByNameAndCategory(name, category.getId());
		}
		
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("selectedCategory", category);
		
		return "search_product";
	}
	
	@RequestMapping("/delete_product/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id) {

		productService.deleteProductById(id);
		
		return "redirect:/product/1";
	}
	
	@RequestMapping("/login_error")
	public String showLoginErrorView(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "Username or password is incorrect");
		System.out.println("login error");
		return "redirect:/login";
	}
	
	@RequestMapping("/signup")
	public String showSignUpView(Model model) {
		return "signup";
	}
	
	@GetMapping("/debug")
	public String showdebugView(Model model) {
		return "debug";
	}
	
	
}
