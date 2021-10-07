package com.bookstore.model.formdata;

import java.beans.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;

public class UserData {
	
	private Integer id;
	
//	private String photoPath;
	
//	@Size(min = 2, max = 64, message = "Please enter your username!")
	private String fullName;
	
//	@Size(min = 4, max = 24, message = "Username length must be between 4 and 24 characters")
	private String username;
	
//	@NotNull(message = "Password cannot be empty")
	@Size(min = 6, max = 61, message = "Password length must be between 6 and 24 characters")
	private String password;
	
	@Size(min = 6, max = 61, message = "Password length must be between 6 and 24 characters")
	private String passwordRetype;
	
	private String avatar;
	
	private Boolean enabled;
	
	private String address;
	
	private String phoneNumber;
	
	private java.sql.Date dateOfBirth;
	
	private String identityNumber;
	
//	@Size(min = 1, message = "Please select role(s) for user!")
	private Set<Role> roles = new HashSet<>();
	
	private String role;
	
	public User updateUserFormData() {
		
		User user = new User();
		
		user.setId(this.id);
		user.setFullName(this.fullName);
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setAvatar(this.avatar);
		user.setEnabled(this.enabled);
		user.setRoles(this.roles);
		user.setAddress(this.address);
		user.setPhoneNumber(this.phoneNumber);
		user.setDateOfBirth(this.dateOfBirth);
		user.setIdentityNumber(this.identityNumber);
		return user;
	}
	
	@Transient
	public void copyValueFromUserEntity(User user) {
		
		this.role = "";
		
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.avatar = user.getAvatar();
		this.roles = user.getRoles();

		for(Role userRole : roles) {
			this.role += userRole.getName() + ", ";
		}
		
	}
	
	@Transient
	public String getPhotoPath() {
		if(avatar == null || avatar.equals("")) {
			return "../images/avatar.jpg";
		}
		if(id != null & avatar != null) {

//			String[] ava = avatar.split("/");
			System.out.println("user data :: " + avatar);
			return "http://localhost:8081/files/" + avatar;
		}
		
		return null;
	}

	public String getPasswordRetype() {
		return passwordRetype;
	}

	public void setPasswordRetype(String passwordRetype) {
		this.passwordRetype = passwordRetype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	
}
