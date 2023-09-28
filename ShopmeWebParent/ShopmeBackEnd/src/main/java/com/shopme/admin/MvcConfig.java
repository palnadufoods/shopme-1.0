package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");

		String CategoryImagesDirName = "../category-images";
		Path CategoryImagesDir = Paths.get(CategoryImagesDirName);

		String CategoryImagesPath = CategoryImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/category-images/**").addResourceLocations("file:/" + CategoryImagesPath + "/");

		String BrandImagesDirName = "brand-photos";
		Path BrandImagesDir = Paths.get(BrandImagesDirName);

		String BrandImagesPath = BrandImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/brand-photos/**").addResourceLocations("file:/" + BrandImagesPath + "/");

		String productImagesDirName = "../product-images";
		Path ProductImagesDir = Paths.get(productImagesDirName);

		String ProductImagesPath = ProductImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/product-images/**").addResourceLocations("file:/" + ProductImagesPath + "/");

		String siteLogoDirName = "../site-logo";
		Path SiteLogoDir = Paths.get(siteLogoDirName);

		String SiteLogoPath = SiteLogoDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/site-logo/**").addResourceLocations("file:/" + SiteLogoPath + "/");

	}
}
