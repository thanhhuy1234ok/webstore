package com.bookstore.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.bookstore.admin.storage.StorageProperties;
import com.bookstore.admin.storage.StorageService;

@SpringBootApplication
//@EnableConfigurationProperties
@EnableConfigurationProperties(StorageProperties.class)
@EntityScan({"com.bookstore.model"})
public class BookStoreAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreAdminApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
//			storageService.deleteAll();
			storageService.init();
		};
	}
}
