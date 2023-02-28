package com.shopme.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggeduser,Model model)
	{
		String email=loggeduser.getUsername();
		User user=userService.getUserByEmail(email);
		model.addAttribute("user", user);
		return "account_form";
	}
	
	@PostMapping("/account/update")
	public String updateUser(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal ShopmeUserDetails loggedUser,
			@RequestParam("image") MultipartFile multiPartFile) throws IOException {
		System.out.println(user);
		for (int i = 0; i < 10; i++)
			System.out.println(multiPartFile.getOriginalFilename());

		if (!multiPartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multiPartFile.getOriginalFilename()); // I think it removes /, \ and
																							// file path.
			fileName = fileName.replaceAll(" ", "");
			user.setPhotos(fileName);
			User savedUser = userService.updateAccount(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
		} else {
			if(user.getPhotos().isEmpty())
				user.setPhotos(null);
			userService.updateAccount(user);
		}
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("message", "The User Details have been Updated Successfully");

		return "redirect:/account";
	}

}
