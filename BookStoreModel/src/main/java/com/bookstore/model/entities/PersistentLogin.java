package com.bookstore.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "series")
	private String series;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "last_used")
	private LocalDateTime last_used;
}
