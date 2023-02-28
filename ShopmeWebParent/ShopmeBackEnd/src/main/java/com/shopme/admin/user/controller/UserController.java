package com.shopme.admin.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/usersall")
	public String listAll(Model model) {
		List<User> listOfUsers = userService.listAll();
		
		model.addAttribute("listOfUsers", listOfUsers);
		return "users";
	}

	@GetMapping("/users")
	public String listFirstPage(Model model) {
		/*
		 * Page<User> page=userService.listByPage(1); List<User>
		 * listOfUsers=page.getContent();
		 * 
		 * System.out.println(page.getTotalElements());
		 * System.out.println(page.getTotalPages()); model.addAttribute("listOfUsers",
		 * listOfUsers); return "users";
		 */

		return "redirect:/users/page/1?sortField=firstName&sortDir=asc";
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(Model model, @PathVariable("pageNum") int pageNumber, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);

		Page<User> page = userService.listByPage(pageNumber, sortField, sortDir, keyword);
		List<User> listOfUsers = page.getContent();

		long startCount = (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", pageNumber);

		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());

		System.out.println("totalItems  " + page.getTotalElements());
		System.out.println("totalPages  " + page.getTotalPages());

		model.addAttribute("listOfUsers", listOfUsers);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		model.addAttribute("keyword", keyword);

		return "users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		List<Role> listRoles = userService.listRoles();
		User user = new User();
		user.setEnabled(true);
		// user.setId(99);
		model.addAttribute("pageTitle", "Create New User");
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageNumber", 1);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		return "user_form";
	}

	@PostMapping("/users/save/{pageNum}")
	public String saveUser(@PathVariable("pageNum") int pageNum, User user, RedirectAttributes redirectAttributes,
			 @Param("sortField") String sortField, @Param("sortDir") String sortDir,@Param("keyword") String keyword,
			@RequestParam("image") MultipartFile multiPartFile) throws IOException {
		System.out.println(user);
		for (int i = 0; i < 10; i++)
			System.out.println(multiPartFile.getOriginalFilename());

		if (!multiPartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multiPartFile.getOriginalFilename()); // I think it removes /, \ and
																							// file path.
			fileName = fileName.replaceAll(" ", "");
			// fileName=user.getId()+"."+multiPartFile.getContentType().substring(6); //
			// optional
			user.setPhotos(fileName);
			User savedUser = userService.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			// System.out.println(multiPartFile.getContentType()); image/png

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
		} else {
			if (user.getPhotos().isEmpty())
				user.setPhotos(null);
			userService.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "The User Details have been Saved Successfully");
		
		return getRedirectUrltoAffectedUser(user);
	}

	private String getRedirectUrltoAffectedUser(User user) {
		
		String firstPartOfEmail=user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstPartOfEmail;
	}

	@GetMapping("/users/edit/{pageNum}/{id}")
	public String updateUser(@PathVariable(name = "pageNum") int pageNum, @PathVariable(name = "id") Integer id,
			Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword,
			RedirectAttributes redirectAttributes) {
		try {
			List<Role> listRoles = userService.listRoles();
			User user = userService.get(id);
			if (user == null)
				throw new UserNotFoundException(" lawada lo ERROR RA IDHI");
			model.addAttribute("pageTitle", "Update User[" + id + "]");
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("pageNumber", pageNum);
			model.addAttribute("keyword", keyword);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			System.out.println("  sort Field >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ sortField);
			System.out.println("  sort Dir >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ sortDir);
			
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users/page/" + pageNum + "?sortField=firstName&sortDir=asc";
		}

	}

	@GetMapping("/users/delete/{pageNum}/{id}")
	public String deleteUser(@PathVariable(name = "pageNum") int pageNum, @PathVariable(name = "id") Integer id,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,@Param("keyword") String keyword,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("message", "User Id " + id + " Deleted Successfully");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users/page/" + pageNum + "?sortField="+sortField+"&sortDir="+sortDir;
	}

	@GetMapping("/users/enable/{pageNum}/{id}/{enabled}")
	public String enabledisable(@PathVariable("pageNum") int pageNum, @PathVariable("id") int id,
			@PathVariable("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < 10; i++)
			System.out.println();
		System.out.println("id =" + id + " enabled=" + enabled);
		userService.updateEnabledStatus(id, enabled);
		if (enabled)
			redirectAttributes.addFlashAttribute("message", "User Id " + id + " has been Enabled");
		else if (!enabled)
			redirectAttributes.addFlashAttribute("message", "User Id " + id + " has been Disabled");
		return "redirect:/users/page/" + pageNum + "?sortField=firstName&sortDir=asc";
	}
	
	@GetMapping("/users/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException
	{
		List<User> listAll=userService.listAll();
		UserCsvExporter exporter=new UserCsvExporter();
		exporter.export(listAll,response);	
	}
	
	
	@GetMapping("/users/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException
	{
		List<User> listAll=userService.listAll();
		UserExcelExporter exporter=new UserExcelExporter();
		exporter.export(listAll,response);
	}
	
	@GetMapping("/users/export/pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException
	{
		List<User> listAll=userService.listAll();
		UserPdfExporter exporter=new UserPdfExporter();
		exporter.export(listAll,response);
	}

}
