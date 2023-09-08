package com.shopme.admin.product.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.shopme.admin.product.ProductService;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	public List<ProductImage> toBeDeleted;

	@GetMapping("/products")
	public String listAll(Model model) {
//		List<Product> listOfProducts = productService.listAll();
//		model.addAttribute("listOfProducts", listOfProducts);
//		return "product/products";
		return "redirect:/products/page/1?sortField=name&sortDir=asc";
	}

	@GetMapping("/products1")
	public String listFirstPage(Model model) {
		/*
		 * Page<User> page=userService.listByPage(1); List<User>
		 * listOfUsers=page.getContent();
		 * 
		 * System.out.println(page.getTotalElements());
		 * System.out.println(page.getTotalPages()); model.addAttribute("listOfUsers",
		 * listOfUsers); return "users";
		 */

		return "redirect:/products/page/1?sortField=name&sortDir=asc";
	}

	@GetMapping("/products/page/{pageNum}")
	public String listByPage(Model model, @PathVariable("pageNum") int pageNumber, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId) {
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);

		Page<Product> page = productService.listByPage(pageNumber, sortField, sortDir, keyword, categoryId);
		List<Product> listOfProducts = page.getContent();

		List<Category> listCategories = // categoryService.listCategoriesUsedInForm();
				categoryService.listCatsInSorting(1, "asc");

		if (categoryId != null)
			model.addAttribute("categoryId", categoryId);
		model.addAttribute("listCategories", listCategories);

		long startCount = (pageNumber - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
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

		model.addAttribute("listOfProducts", listOfProducts);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		model.addAttribute("keyword", keyword);

		model.addAttribute("pageNumber", pageNumber);

		return "product/products";
	}

	@GetMapping("/products/new")
	public String newUser(Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		Product product = new Product();

		product.setEnabled(true);
		product.setInStock(true);
		List<Brand> listOfBrands = brandService.listAllBrands();

		Integer noOfExistingExtraImages = product.getImages().size() + 1;
		for (int i = 0; i < 10; i++)
			System.out.println(noOfExistingExtraImages);

		model.addAttribute("pageTitle", "Create New Product");
		model.addAttribute("listOfBrands", listOfBrands);

		model.addAttribute("pageNumber", 1);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("product", product);
		model.addAttribute("noOfExistingExtraImages", noOfExistingExtraImages);

		return "product/product_form";
	}

	@PostMapping("/products/save")
	public String saveProduct(Product product, RedirectAttributes redirectAttributes,
			@RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
			@RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,
			@AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {

		if (!loggedUser.hasRole("admin") && !loggedUser.hasRole("Editor")) {
			if (loggedUser.hasRole("SalesPersion")) {
				productService.saveProductPrice(product);
				redirectAttributes.addFlashAttribute("message", "The product has been saved successfully.");
				// return "redirect:/products";
				return "redirect:/products/page/1?sortField=name&sortDir=asc";
			}
		}

		for (int i = 0; imageIDs != null && i < imageIDs.length; i++) {
			System.out.println("----> " + imageIDs[i] + "  " + imageNames[i]);
		}

		ProductSaveHelper.setMainImageName(mainImageMultipart, product);
		ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
		ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
		ProductSaveHelper.setProductDetails(detailIDs, detailNames, detailValues, product);

		Product savedProduct = productService.save(product);

		ProductSaveHelper.deleteExtraImagesWereRemovedOnForm(product);

		ProductSaveHelper.saveUploadImages(mainImageMultipart, extraImageMultiparts, savedProduct);

		redirectAttributes.addFlashAttribute("message", "the product has been saved successfully");
		// return "redirect:/products";

		return "redirect:/products/page/1?sortField=name&sortDir=asc";
	}

	@GetMapping("/products/enable/{id}")
	public String updateProductEnabledStatus(@PathVariable("id") Integer id, @Param("enabled") boolean enabled,
			RedirectAttributes redirectAttributes) {

		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "Enabled" : "Disabled";
		String message = "The Product Id " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/products";
	}

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			productService.delete(id);
			String productExtraImagesDir = "../product-images/" + id + "/extras";
			String productImagesDir = "../product-images/" + id;

			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);

			redirectAttributes.addFlashAttribute("message", "The Product ID " + id + " has been deleted successfully");
		} catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/products";
	}

	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Product product = productService.get(id);
			List<Brand> listOfBrands = brandService.listAllBrands();

			Integer noOfExistingExtraImages = product.getImages().size() + 1;
			for (int i = 0; i < 10; i++)
				System.out.println(noOfExistingExtraImages);

			model.addAttribute("product", product);
			model.addAttribute("listOfBrands", listOfBrands);
			model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
			model.addAttribute("noOfExistingExtraImages", noOfExistingExtraImages);

			return "product/product_form";

		} catch (com.shopme.common.exception.ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/products";
		}
	}

	@GetMapping("/products/detail/{id}")
	public String viewProductDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Product product = productService.get(id);
			model.addAttribute("product", product);

			return "product/product_detail_modal";

		} catch (com.shopme.common.exception.ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/products";
		}
	}

}
