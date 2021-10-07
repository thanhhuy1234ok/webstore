package com.bookstore.admin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.admin.helper.FileUploadHelper;
import com.bookstore.admin.helper.PasswordManager;
import com.bookstore.admin.services.RoleService;
import com.bookstore.admin.services.UserService;
import com.bookstore.admin.storage.StorageService;
import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;
import com.bookstore.model.formdata.UserData;
import com.bookstore.model.formdata.UserDataForConfirmPassword;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private final StorageService storageService;
	
	@Autowired
	public UserController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/user")
	public String showUsersView(Model model) {
		List<User> listUsers = userService.getAllUsers();
		
		List<UserData> copyListUser = new ArrayList<UserData>();
		
		for(User user : listUsers) {
			copyListUser.add(user.copyValueFromUserEntity());
			
		}
		
		
		
		model.addAttribute("listUsers", copyListUser);
		return "users";
	}
	
	@GetMapping("/create_user")
	public String showCreateNewUserView(Model model) {
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", roleService.getAllRoles());
		return "create_user";
	}
	
	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public String checkNewUserInfo(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listRoles", roleService.getAllRoles());
			return "create_user";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

//		if(!fileName.equals("")) {
//			user.setAvatar("profile-photosslash" + user.getId() + "slash" + fileName);
//			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
//			
//			try {
//				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}	
		user.setEnabled(true);
		userService.saveUser(user);
		
		if(!fileName.equals("")) {
			user.setAvatar("profile-photosslash" + user.getId() + "slash" + fileName);
			String uploadDir = "profile-photos/" + user.getId();
			storageService.store(uploadDir, multipartFile);
			userService.saveUser(user);
		}	
		
		return "redirect:/user";
	}
	
	//*************************
	//*       Edit User       *
	//*************************
	
	@RequestMapping(value = "/edit_user/{id}", method = RequestMethod.GET)
	public String showEditUserView(@PathVariable(name = "id") Integer id, Model model) {
		
		User user = userService.getUserById(id);
		if(user != null) {
			List<Role> listRoles = roleService.getAllRoles();
			
			model.addAttribute("user",user);
			model.addAttribute("listRoles",listRoles);
			return "edit_user";
		}
		
		return "redirect:/user";
	}
	
	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public String checkExistUserInfo(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
		
		if(bindingResult.hasErrors()) {
			List<Role> listRoles = roleService.getAllRoles();
			model.addAttribute("listRoles",listRoles);
			return "/edit_user";
		}
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			user.setAvatar("profile-photosslash" + user.getId() + "slash" + fileName);
			String uploadDir = "profile-photos/" + user.getId();
			storageService.store(uploadDir, multipartFile);
		}

		userService.saveUser(user);
		return "redirect:/user";
	}
	
	@RequestMapping(value = "/confirm_old_password", method = RequestMethod.GET)
	public String checkUserPassword(Model model) {
		
		UserDataForConfirmPassword userData = new UserDataForConfirmPassword();
		
		model.addAttribute("userData", userData);
		
		return "confirm_old_password";
	}
	
	@RequestMapping(value = "/confirm_old_password", method = RequestMethod.POST)
	public String checkUserPassword(@ModelAttribute("userData") UserDataForConfirmPassword userData, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		Boolean error = false;
		
		if(bindingResult.hasErrors()) {
//			UserDataForConfirmPassword userData = new UserDataForConfirmPassword();
//			model.addAttribute("userData", userData);
			return "/confirm_old_password";
		}
		
		User oldUser = getCurrentUser();
		String oldPassword = oldUser.getPassword();

		if(!PasswordManager.checkPassword(userData.getPassword(), oldPassword)) {
			redirectAttributes.addFlashAttribute("error_wrong_password", "The password you entered is incorrect.");
			error = true;
		}
		
		if(error) {
			return "redirect:/confirm_old_password";
		}
		
		return "redirect:/change_password";
	}
	
	@RequestMapping(value = "/change_password", method = RequestMethod.GET)
	public String showChangePasswordView(Model model) {
		
		UserData userData = new UserData();
		
		model.addAttribute("userData", userData);
		
		return "change_password";
	}
	
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String checkNewPassword(@Valid UserData userData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		Boolean error = false;

		if(bindingResult.hasErrors()) {
			System.out.println("not enough charater");
			return "/change_password";
		}
		
		User oldUser = getCurrentUser();

		if(!((userData.getPassword()).equals(userData.getPasswordRetype()))) {
			redirectAttributes.addFlashAttribute("error_match_password", "The password you entered is not matched.");
			error = true;
		}
		
		if(PasswordManager.checkPassword(userData.getPassword(), oldUser.getPassword())) {
			redirectAttributes.addFlashAttribute("error_match_old_password", "Please use a new password");
			error = true;
		}
		
		if(error) {
			return "redirect:/change_password";
		}
		
		oldUser.setPassword(PasswordManager.getBCrypPassword(userData.getPasswordRetype()));
		
		userService.saveUser(oldUser);
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfileView(Model model) {
		
		User user = getCurrentUser();
		
		model.addAttribute("user", user);
		
		return "profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String checkUserInfo(@RequestParam("fileImage") MultipartFile multipartFile, @ModelAttribute("user") User user) {
		
		System.out.println("checkUserInfo :: " + user.getPhotoPath());
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		if(!fileName.equals("")) {
			user.setAvatar(fileName);
			String uploadDir = AppConstant.PROFILE_PHOTO_DIR + "/" + user.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		user.setEnabled(true);
		
		userService.saveUser(user);
		return "redirect:/profile";
	}
	
	@Transient
	public User getCurrentUser() {
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			 username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
			
		User currentUser = userService.getUserByUsername(username);

		return currentUser;
	}
	
	@GetMapping(value = "/search_user")
	public String searchUserView(Model model, @Param("username") String username) {
		User user = userService.fullTextSearchUsername(username);
		UserData userData = new UserData();
		if(user != null) {
			userData = user.copyValueFromUserEntity();
			model.addAttribute("listUsers", userData);
		}
		
		return "search_user";
	}
	
	@RequestMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id) {
		
		userService.deleteUserById(id);
		
		return "redirect:/user";
	}
}
