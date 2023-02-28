package com.shopme.admin.brand.controller;

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
import com.shopme.admin.brand.BrandService;
import com.shopme.admin.brand.export.BrandCsvExporter;
import com.shopme.admin.brand.export.BrandExcelExporter;
import com.shopme.admin.brand.export.BrandPdfExporter;
import com.shopme.admin.category.CategoryService;

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/brandsall")
	public String listAll(Model model) {
		List<Brand> listOfBrands = brandService.listAll();
		model.addAttribute("listOfBrands", listOfBrands);
		return "brand/brands";
	}

	@GetMapping("/brands")
	public String listFirstPage(Model model) {
		/*
		 * Page<User> page=userService.listByPage(1); List<User>
		 * listOfUsers=page.getContent();
		 * 
		 * System.out.println(page.getTotalElements());
		 * System.out.println(page.getTotalPages()); model.addAttribute("listOfUsers",
		 * listOfUsers); return "users";
		 */

		return "redirect:/brands/page/1?sortField=name&sortDir=asc";
	}

	@GetMapping("/brands/page/{pageNum}")
	public String listByPage(Model model, @PathVariable("pageNum") int pageNumber, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);

		Page<Brand> page = brandService.listByPage(pageNumber, sortField, sortDir, keyword);
		List<Brand> listOfBrands = page.getContent();

		long startCount = (pageNumber - 1) * BrandService.BRANDS_PER_PAGE + 1;
		long endCount = startCount + BrandService.BRANDS_PER_PAGE - 1;
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

		model.addAttribute("listOfBrands", listOfBrands);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		model.addAttribute("keyword", keyword);

		model.addAttribute("pageNumber", pageNumber);

		return "brand/brands";
	}

	@GetMapping("/brands/new")
	public String newUser(Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		List<Category> listOfCategories = categoryService.listCatsInSorting(0, "asc");
		Brand brand = new Brand();
		model.addAttribute("pageTitle", "Create New Brand");
		model.addAttribute("brand", brand);
		model.addAttribute("listOfCategories", listOfCategories);
		model.addAttribute("pageNumber", 1);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		return "brand/brand_form";
	}

	@PostMapping("/brands/save")
	public String saveBrand(/* @PathVariable("pageNum") int pageNum, */ Brand brand,
			RedirectAttributes redirectAttributes, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,
			@RequestParam("image") MultipartFile multiPartFile) throws IOException {
		System.out.println(brand);
		for (int i = 0; i < 10; i++)
			System.out.println(multiPartFile.getOriginalFilename());

		if (!multiPartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multiPartFile.getOriginalFilename()); // I think it removes /, \ and
																							// file path.
			fileName = fileName.replaceAll(" ", "");
			// fileName=user.getId()+"."+multiPartFile.getContentType().substring(6); //
			// optional
			brand.setLogo(fileName);
			Brand savedBrand = brandService.save(brand); // save
			String uploadDir = "brand-photos/" + savedBrand.getId();
			// System.out.println(multiPartFile.getContentType()); image/png

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
		} else {
			if (brand.getLogo().isEmpty())
				brand.setLogo(null);
			brandService.save(brand);
		}
		redirectAttributes.addFlashAttribute("message", "The Brand Details have been Saved Successfully");

		return getRedirectUrltoAffectedBrand(brand);
	}

	public String getRedirectUrltoAffectedBrand(Brand brand) {

		// String name=brand.getName();
		// return "redirect:/brands/page/1?sortField=name&sortDir=asc&keyword="+name;
		// return "redirect:/brands/page/1?sortField=name&sortDir=asc";
		return "redirect:/brands/page/1?sortField=name&sortDir=asc";
	}

	@GetMapping("/brands/edit/{pageNum}/{id}")
	public String updateBrand(@PathVariable(name = "pageNum") int pageNum, @PathVariable(name = "id") Integer id,
			Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword, RedirectAttributes redirectAttributes) {
		try {

			Brand brand = brandService.findById(id);

			if (brand == null)
				throw new UserNotFoundException(" lawada lo ERROR RA IDHI");

			model.addAttribute("pageTitle", "Update Brand[" + id + "]");
			model.addAttribute("brand", brand);
			model.addAttribute("pageNumber", pageNum);
			model.addAttribute("keyword", keyword);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("listOfCategories", categoryService.listCatsInSorting(0, "asc"));
			System.out.println("  sort Field >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + sortField);
			System.out.println("  sort Dir >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + sortDir);

			return "brand/brand_form";

		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/brands/page/" + pageNum + "?sortField=firstName&sortDir=asc";
		}

	}

	@GetMapping("/brands/delete/{pageNum}/{id}")
	public String deleteBrand(@PathVariable(name = "pageNum") int pageNum, @PathVariable(name = "id") Integer id,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword,
			Model model, RedirectAttributes redirectAttributes) {

		brandService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Brand Id " + id + " Deleted Successfully");

		// return "redirect:/brands/page/" + pageNum + "?sortField=" + sortField +
		// "&sortDir=" + sortDir;
		return "redirect:/brands/page/" + pageNum + "?sortField=name&sortDir=asc";
	}

	@GetMapping("/brands/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Brand> listAll = brandService.listAll();

		BrandCsvExporter exporter = new BrandCsvExporter();
		exporter.export(listAll, response);
	}

	@GetMapping("/brands/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<Brand> listAll = brandService.listAll();
		BrandExcelExporter exporter = new BrandExcelExporter();
		exporter.export(listAll, response);
	}

	@GetMapping("/brands/export/pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException {
		List<Brand> listAll = brandService.listAll();
		BrandPdfExporter exporter = new BrandPdfExporter();
		exporter.export(listAll, response);
	}
}
