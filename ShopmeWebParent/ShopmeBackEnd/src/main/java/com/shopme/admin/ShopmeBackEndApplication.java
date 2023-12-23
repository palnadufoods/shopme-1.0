package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;



@SpringBootApplication(scanBasePackages = { "com.shopme.admin", "com.shopme.common" })
@EntityScan({ "com.shopme.common.entity", "com.shopme.admin" })

// @EnableMongoRepositories(basePackages = {
// 		"com.shopme.common.ProductMgo.repo0", "com.shopme.common.ProductMgo.repo1",
// 		"com.shopme.common.ProductMgo.repo2", "com.shopme.common.ProductMgo.repo3",
// 		"com.shopme.common.ProductMgo.repo4", "com.shopme.common.ProductMgo.repo5",
// 		"com.shopme.common.ProductMgo.repo6", "com.shopme.common.ProductMgo.repo7",
// 		"com.shopme.common.ProductMgo.repo8", "com.shopme.common.ProductMgo.repo9" })

// @ComponentScan(basePackages = "com.shopme.common.MongodbConfig")

//@EnableMongoRepositories("com.shopme.common.ProductMgo") // Add the package
// containing MongoDB repositories

// @ComponentScan(basePackages = { "com.shopme.common.MongodbConfig", "com.shopme.common.ProductMgo.repo0",
// 		"com.shopme.admin" })

//@Import({MongoMultiConfig.class, MongoMultiConfig1.class})
public class ShopmeBackEndApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ShopmeBackEndApplication.class, args);

		// Get the names of all beans in the context

		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println("Bean Name: " + beanName);
		}

	}

}
