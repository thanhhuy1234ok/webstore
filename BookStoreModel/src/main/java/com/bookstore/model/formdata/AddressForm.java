package com.bookstore.model.formdata;

import javax.persistence.Transient;

import com.bookstore.model.entities.Address;

public class AddressForm {

	private String city;
	
	private String district;
	
	private String ward;
	
	private String street;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@Transient
	public Address copyToAddressEntity(Address address) {
		address.setStreet(street);
		address.setWard(ward);
		address.setDistrict(district);
		address.setCity(city);
		
		return address;
	}
}
