package com.bookstore.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookstore.admin.repository.UserRepository;
import com.bookstore.model.entities.Role;
import com.bookstore.model.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
//		System.out.println("UserDetailsServiceImpl::");
		if(user == null) {
			throw new UsernameNotFoundException("Ten dang nhap khong ton tai");
		}
		System.out.println("fullname: " + user.getFullName());
		System.out.println("password: " + user.getPassword());
		System.out.println("username: " + user.getUsername());
//		System.out.println(user.getEnabled());
		System.out.println("id: " + user.getId());
		
		for(Role role : user.getRoles()) {
			System.out.println("role: " + role.getName());
		}
		
		return new MyUserDetails(user);
	}

}
