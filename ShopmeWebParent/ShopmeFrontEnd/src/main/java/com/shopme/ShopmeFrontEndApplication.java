package com.shopme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
//@EnableMongoRepositories("com.shopme.common.ProductMgo")
public class ShopmeFrontEndApplication {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ShopmeFrontEndApplication.class, args);
		String[] beanNames = context.getBeanDefinitionNames();
		
		for (String beanName : beanNames) {
			System.out.println("Bean Name: " + beanName);
		}
	}
}
