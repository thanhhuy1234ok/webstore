package com.bookstore.admin.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.admin.repository.UserRepository;
import com.bookstore.model.entities.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(Integer id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return userRepository.getById(id);
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}
	
	public User fullTextSearchUsername(String username){
		return userRepository.fullTextSearchUsername(username);
	}
	
	public User getUserByIdentityNumber(String identityNumber) {
		return userRepository.getUserByIdentityNumber(identityNumber);
	}
	
	public User getUserByPhoneNumber(String phoneNumber) {
		return userRepository.getUserByPhoneNumber(phoneNumber);
	}
	
	public void saveUser(User user) {
		
		Pattern bcryptPasswordPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");  
		
		if(!bcryptPasswordPattern.matcher(user.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			
			user.setPassword(encodePassword);
		}
		
		
		userRepository.save(user);
	}
	
	public void deleteUserById(Integer id) {
		Optional<User> optionalUser = userRepository.findById(id);
		optionalUser.ifPresent(user -> userRepository.deleteById(id));
	}
	
	public Boolean checkUsernameExist(String username) {
		if(getUserByUsername(username) != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkIdentityNumberExist(String identityNumber) {
		if(getUserByIdentityNumber(identityNumber) != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkUserIdentityNumberExist(String identityNumber, Integer id) {
		User user = getUserByIdentityNumber(identityNumber);
		
		if(user != null && user.getId() != id) {
			return true;
		}
		return false;
	}
	
	public Boolean checkPhoneNumberExist(String phoneNumber) {
		if(getUserByPhoneNumber(phoneNumber) != null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkUserPhoneNumberExist(String phoneNumber, Integer id) {
		User user = getUserByPhoneNumber(phoneNumber);
		
		if(user != null && user.getId() != id) {
			return true;
		}
		return false;
	}
}
