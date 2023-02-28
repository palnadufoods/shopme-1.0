package com.shopme.admin.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.category.CategoryService;
import com.shopme.admin.user.UserService;

@RestController
public class CategoryRestController {
	
	
	@Autowired
	public CategoryService service;
	
	@PostMapping("/category/check_unique")
	public String checkDuplicateEmail(@Param("id") String id, @Param("name") String name,@Param("alias") String alias)
	{
		for(int i=0;i<10;i++)
		System.out.println(id);
		int id1=0;
		if(id==null||id.isEmpty())
			id1=0;
		else
			id1=Integer.parseInt(id);
		return service.checkUnique(id1, name, alias);
	}
}
