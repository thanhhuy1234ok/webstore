package com.bookstore.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.bookstore.model.enumerate.AuthProvider;

@Entity
@Table(name = "customers")
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6381240746290010920L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "email")
	@Email(message = "Invalid Email")
	private String email;

	
	@Column(name = "first_name")
	@Size(min = 1, max = 64, message = "Please enter first name")
	private String firstName;
	
	@Column(name = "last_name")
	@Size(min = 1, max = 64, message = "Please enter last name")
	private String lastName;
	
	@Column(name = "date_of_birth")
	private java.sql.Date dateOfBirth;
	
	@Column(name = "password")
	@Size(min = 8, max = 60, message = "Password length must be between 8 and 24 characters")
	private String password;
	
	@Column(name = "phone_Number")
	@Size(min = 10, max = 12, message = "Invalid phone number")
	private String phoneNumber;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Address> addresses = new HashSet<>();
	
	@OneToMany(mappedBy = "customerInvoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Invoice> invoices = new HashSet<>();
	
	@Column(name = "photo_url")
	private String photoUrl;
	
	@Column(name = "customer_rank")
	private String rank;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "last_login")
	private Date lastLogin;
	
	@Column(name = "email_verified")
	private Boolean emailVerified;
	
	@Column(name = "verification_code")
	private String verificationCode;
	
	@Column(name = "auth_provider")
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;
	
	@Column(name = "enabled")
	private Boolean enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public java.sql.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public Set<Address> getAddress() {
		return addresses;
	}

	public void setAddress(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Transient
	public String getPhotoPath() {
		if(photoUrl == null || photoUrl.equals("")) {
			return "../images/avatar.jpg";
		}
		if(id != null & (photoUrl != null || !photoUrl.equals(""))) {
			return "/files/" + photoUrl;
		}
		return null;
	}
}
