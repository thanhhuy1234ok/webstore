package com.bookstore.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.admin.services.RoleService;
import com.bookstore.model.entities.Role;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/role")
	public String showRoleView(Model model) {
		List<Role> listRole = roleService.getAllRoles();
		
		model.addAttribute("listRoles", listRole);
		
		return "roles";
	}
	
	@GetMapping("/create_role")
	public String showCreateRoleView(Model model) {
		Role role = new Role();
		
		model.addAttribute("role", role);
		
		return "create_role";
	}
	
	@RequestMapping(value = "/create_role", method = RequestMethod.POST)
	public String checkRoleInfo(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "create_role";
		}
		
		roleService.save(role);
		
		return "redirect:/role";
	}
	
	@RequestMapping(value = "/edit_role/{id}", method = RequestMethod.GET)
	public String showEditRoleView(@PathVariable(name = "id") Integer id, Model model) {
		
		Role role = roleService.getRoleById(id);
		
		model.addAttribute("role", role);
		
		return "edit_role";
		
	}
	
	@RequestMapping(value = "/edit_role", method = RequestMethod.POST)
	public String checkExistRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "edit_role";
		}
		System.out.println("checkExistRole :: " + role.getId());
		roleService.save(role);
		
		return "redirect:/role";
	}
	
	@RequestMapping(value = "/delete_role/{id}")
	public String deleteRole(@PathVariable(name = "id") Integer id) {
		
		roleService.deleteRoleById(id);
		System.out.println("DELETE ROLE-----------------------------------");
		
		return "redirect:/role";
	}
	
	@GetMapping("/search_role")
	public String searchRoleView(Model model, @Param("name") String name) {
		List<Role> listRoles = roleService.fullTextSearchRoleByName(name);
		model.addAttribute("listRoles", listRoles);
		return "search_role";
	}
}
