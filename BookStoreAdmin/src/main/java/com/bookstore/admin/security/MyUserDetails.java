package com.bookstore.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;

public class MyUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public MyUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		
		String message = "user:" + user.getUsername() + " has role: ";
		String messageRoles = "";
		for (Role role : roles) {
			messageRoles = messageRoles + "-" + role.getName();
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		System.out.println(message + messageRoles);

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	public String getFullName() {
		return user.getFullName();
	}
	
	public Integer getId() {
		return user.getId();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}

}
