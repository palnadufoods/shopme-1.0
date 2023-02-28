package com.shopme.admin.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.product.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	public ProductService service;

	@PostMapping("/products/check_unique")
	public String checkDuplicateName(@Param("id") String id, @Param("name") String name) {

		int id1 = 0;
		if (id == null || id.isEmpty())
			id1 = 0;
		else
			id1 = Integer.parseInt(id);
		return service.checkUnique(id1, name);
	}
}
