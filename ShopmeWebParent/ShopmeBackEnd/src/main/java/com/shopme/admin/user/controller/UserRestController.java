package com.shopme.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.user.UserService;

@RestController
public class UserRestController {
	
	
	@Autowired
	public UserService userService;
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") String id, @Param("email") String email)
	{
		int id1;
		if(id=="")
			id1=0;
		else
			id1=Integer.parseInt(id);
		return userService.isEmailUnique(id1,email)? "Ok":"Duplicated";
	}
}
