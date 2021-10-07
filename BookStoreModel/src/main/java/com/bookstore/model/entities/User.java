package com.bookstore.model.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.bookstore.model.formdata.UserData;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "full_name")
	@Size(min = 1, max = 64, message = "Please enter your fullname")
	private String fullName;
	
	@Column(name = "username")
	@Size(min = 4, max = 24, message = "Username length must be between 4 and 24 characters")
	private String username;
	
	@Column(name = "password")
	@Size(min = 6, max = 65, message = "Password length must be between 6 and 24 characters")
	private String password;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	@Size(min = 10, max = 10, message = "Invalid phone number")
	private String phoneNumber;
	
	@Column(name = "date_of_birth")
	private java.sql.Date dateOfBirth;
	
	@Column(name = "identity_number")
	@Size(min = 9, max = 12, message = "Invalid ID number")
	private String identityNumber;
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Size(min = 1, message = "Please select role(s) for user!")
	private Set<Role> roles = new HashSet<>();

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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Transient
	public UserData copyValueFromUserEntity() {
		
		UserData userData = new UserData();
		
		String role = "";
		
		userData.setId(this.id);
		userData.setFullName(fullName);
		userData.setUsername(username);
		userData.setPassword(password);
		userData.setRoles(roles);
		userData.setAvatar(avatar);
		userData.setAddress(address);
		userData.setDateOfBirth(dateOfBirth);
		userData.setIdentityNumber(identityNumber);
		userData.setPhoneNumber(phoneNumber);

		for(Role userRole : roles) {
			role += userRole.getName() + ", ";
		}
		
		userData.setRole(role.substring(0, role.length() - 2));
		
		return userData;
	}
	
	@Transient
	public String getPhotoPath() {
		if(avatar == null || avatar.equals("")) {
			return "../images/avatar.jpg";
		}
		if(id != null & (avatar != null || avatar.equals(""))) {
			return "http://localhost:8081/files/" + avatar;
		}
		return null;
	}

}
