package com.bookstore.admin.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.services.RoleService;

@RestController
public class RoleRestController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/api/role_name")
	public String checkRoleNameExist(@RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleId") Integer roleId) {
		return roleService.checkRoleNameExist(roleName, roleId)?"This name has been taken!":"";
	}
	
	@GetMapping("/api/new_role_name")
	public String checkRoleNameExist(@RequestParam(value = "roleName") String roleName) {
		return roleService.checkNewRoleNameExist(roleName)?"This name has been taken!":"";
	}
}
