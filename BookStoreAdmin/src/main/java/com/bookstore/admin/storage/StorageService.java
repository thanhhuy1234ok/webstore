package com.bookstore.admin.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.admin.handler.AppConstant;
import com.bookstore.model.enumerate.EntityType;

public interface StorageService {

	void init();
	
//	void store(MultipartFile file, String photoPath);
	void store(String uploadDir, MultipartFile file);
	
	Stream<Path> loadAll();
	
	Path load(String filename);
	
	Resource loadAsResource(String filename);
	
	void deleteAll();
}
