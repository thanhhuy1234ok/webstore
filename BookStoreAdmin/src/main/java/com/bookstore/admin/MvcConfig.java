package com.bookstore.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bookstore.admin.handler.AppConstant;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		exposeDirectory(AppConstant.PROFILE_PHOTO_DIR, registry);
		exposeDirectory(AppConstant.PRODUCT_PHOTO_DIR, registry);
		exposeDirectory(AppConstant.CUSTOMER_PHOTO_DIR, registry);
		exposeDirectory(AppConstant.CATEGORY_PHOTO_DIR, registry);
	}
	
	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);

//		String uploadPath = uploadDir.toFile().getAbsolutePath();
		String uploadPath2 = "";
		try {
			uploadPath2 = uploadDir.toFile().getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(dirName.startsWith("../")) {
			dirName.replace("../", "");
		}
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + uploadPath2 + "/");
	}
}
