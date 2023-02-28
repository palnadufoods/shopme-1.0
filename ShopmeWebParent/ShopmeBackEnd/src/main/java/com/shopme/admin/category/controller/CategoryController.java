package com.shopme.admin.category.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.category.export.CategoryCsvExporter;
import com.shopme.admin.category.export.CategoryExcelExporter;
import com.shopme.admin.category.export.CategoryPdfExporter;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class CategoryController {

	@Autowired
	public CategoryService service;

	@GetMapping("/categories")
	public String listCategories(Model model,@Param("sortDir") String sortDir) {
		
		if(sortDir==null||sortDir.isEmpty())
		{
			sortDir="asc";
		}
		/*
		Page<Category> pageCategory =service.listByPage(1, sortDir);
		
		List<Category> listOfCategories=pageCategory.getContent();
		//service.listCatsInSorting(0,sortDir)
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("listOfCategories", listOfCategories);
		return "category/categories";
		*/
		return "redirect:/categories/page/1?sortDir="+sortDir;
	}
	@GetMapping("/categories/page/{pageNum}")
	public String listCategories1(Model model,@PathVariable("pageNum") int pageNumber,@Param("sortDir") String sortDir,@Param("keyword") String keyword) {
		
		if(sortDir==null||sortDir.isEmpty())
		{
			sortDir="asc";
		}
		List<Category> listOfCategories=null;
		if(keyword==null||keyword.isEmpty())
		{
			listOfCategories=service.listByPage(pageNumber, sortDir);
		}
		else {
			listOfCategories=service.search(pageNumber, sortDir, keyword);
		}
		
		//service.listCatsInSorting(0,sortDir)
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("listOfCategories", listOfCategories);
		
		

		long startCount = (pageNumber - 1) * CategoryService.CATEGORIES_PER_PAGE + 1;
		long endCount = startCount + CategoryService.CATEGORIES_PER_PAGE - 1;
		if (endCount > CategoryService.totalElements) {
			endCount = CategoryService.totalElements;
		}
		
		model.addAttribute("sortField", "name");
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", pageNumber);

		model.addAttribute("totalItems", CategoryService.totalElements);
		model.addAttribute("totalPages", CategoryService.totalPages);

		model.addAttribute("keyword", keyword);
		
		return "category/categories";
	}

	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("listOfCategories",service.listCatsInSorting(1,"asc"));
		model.addAttribute("pageTitle", "Create a New Category");
		return "category/category_form";
	}

	@PostMapping("/categories/save")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {

		if (!multipartFile.isEmpty()) {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);
			for(int i=0;i<10;i++)
				System.out.println();
			System.out.println("hello "+category.getImage());
			Category savedCategory = service.save(category);

			String uploadDir = "../category-images/" + savedCategory.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			
			for(int i=0;i<10;i++)
				System.out.println();
			System.out.println(category.getImage());
			service.save(category);
		}

		redirectAttributes.addFlashAttribute("message", "Category have been saved successfully");
		return "redirect:/categories";
	}

	@GetMapping("/categories/edit/{id}")
	public String updateUser(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Category category = service.get(id);
			if (category == null)
				throw new UserNotFoundException(" lawada lo ERROR RA IDHI");

			model.addAttribute("pageTitle", "Update Category[" + id + "]");
			model.addAttribute("category", category);
			model.addAttribute("listOfCategories", service.listCatsInSorting(1,"asc"));
			return "category/category_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/categories";
		}
	}
	@GetMapping("/categories/enable/{id}")
	public String enableOrDisable(@PathVariable(name = "id") Integer id,Model model,@Param("enabled") boolean enabled,RedirectAttributes redirectAtrributes)
	{
		service.UpdateEnabledStatus(id, enabled);
		String enbleOrDisable=enabled==true?"Enabled ":"Disabled";
		redirectAtrributes.addFlashAttribute("message", "Category Id "+id+" has been "+enbleOrDisable+" Sucessfully");
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(Model model,@PathVariable("id") Integer id,RedirectAttributes redirectAtrributes)
	{
		service.deleteById(id);
		String categoryDir="../category-images/"+id;
		FileUploadUtil.removeDir(categoryDir);
		redirectAtrributes.addFlashAttribute("message", "Category Id "+id+" has been Deleted Sucessfully");
		
		return "redirect:/categories";
	}
	

	@GetMapping("/categories/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException
	{
		List<Category> listAll=service.listAll();
		
		CategoryCsvExporter exporter=new CategoryCsvExporter();
		exporter.export(listAll, response);
	}
	
	
	@GetMapping("/categories/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException
	{
		List<Category> listAll=service.listAll();
		CategoryExcelExporter exporter=new CategoryExcelExporter();
		exporter.export(listAll,response);
	}
	
	@GetMapping("/categories/export/pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException
	{
		List<Category> listAll=service.listAll();
		CategoryPdfExporter exporter=new CategoryPdfExporter();
		exporter.export(listAll, response);
	}


}
