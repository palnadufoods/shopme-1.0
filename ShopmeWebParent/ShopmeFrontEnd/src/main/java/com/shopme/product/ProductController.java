package com.shopme.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.mongo.ProductMgo;
import com.shopme.common.exception.CategoryNotFoundException;
import com.shopme.common.exception.ProductNotFoundException;
import com.shopme.productMgo.CustomPage;
import com.shopme.productMgo.ProductMgoService;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductMgoService productMgoService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		return viewCategoryByPage(alias, 1, model);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias, @PathVariable("pageNum") int pageNum,
			Model model) {
		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);

			System.out.println("totalPages=" + pageProducts.getTotalPages());
			System.out.println("currentPage=" + pageNum);

			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model,
			HttpServletRequest request) {

		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			// Page<Review> listReviews =
			// reviewService.list3MostVotedReviewsByProduct(product);

			// Customer customer = controllerHelper.getAuthenticatedCustomer(request);

//			if (customer != null) {
//				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
//				voteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(),
//						customer.getId());
//
//				if (customerReviewed) {
//					model.addAttribute("customerReviewed", customerReviewed);
//				} else {
//					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
//					model.addAttribute("customerCanReview", customerCanReview);
//				}
//			}

			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			// model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}

	}

	@GetMapping("/search")
	public String searchFirstPage(String keyword, Model model) {
		// return searchByPage(keyword, 1, model);
		return searchByPageMgo(keyword, 1, model);
	}

	@GetMapping("/searchMgo")
	public String searchFirstPage1(String keyword, Model model) {
		// return searchByPage(keyword, 1, model);
		return searchByPageMgo(keyword, 1, model);
	}

	@GetMapping("/mgo")
	public String findingMgoProducts(Model model) {
		// List<ProductMgo> pros = productMgoService.getAllProducts();
		/*
		 * List<ProductMgo> pros = productMgoService.searchProductsMgo("Guru"); for
		 * (ProductMgo pro : pros) { System.out.println(pro.getName() + " .......... " +
		 * pro.getId()); }
		 */

		return "redirect:/";
	}

	// Mysql db Call

	/*
	 * @GetMapping("/search/page/{pageNum}") public String searchByPage(String
	 * keyword, @PathVariable("pageNum") int pageNum, Model model) { Page<Product>
	 * pageProducts = productService.search(keyword, pageNum); List<Product>
	 * listResult = pageProducts.getContent();
	 * 
	 * long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
	 * long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1; if
	 * (endCount > pageProducts.getTotalElements()) { endCount =
	 * pageProducts.getTotalElements(); }
	 * 
	 * model.addAttribute("currentPage", pageNum); model.addAttribute("totalPages",
	 * pageProducts.getTotalPages()); model.addAttribute("startCount", startCount);
	 * model.addAttribute("endCount", endCount); model.addAttribute("totalItems",
	 * pageProducts.getTotalElements()); model.addAttribute("pageTitle", keyword +
	 * " - Search Result");
	 * 
	 * model.addAttribute("keyword", keyword); model.addAttribute("searchKeyword",
	 * keyword); model.addAttribute("listResult", listResult);
	 * 
	 * return "product/search_result"; }
	 */

	@GetMapping("/searchMgo/page/{pageNum}")
	public String searchByPageMgo(String keyword, @PathVariable("pageNum") int pageNum, Model model) {
		// Page<ProductMgo> pageProducts =
		// productMgoService.searchProductsMgo(keyword,pageNum);
		// productService.search(keyword, pageNum);

		CustomPage<ProductMgo> pageProducts = productMgoService.searchProductsMgo(keyword, pageNum);

		List<ProductMgo> listResult = pageProducts.getProductsMgo(); // pageProducts.getContent();
		/*
		 * for(ProductMgo productMgo:listResult) {
		 * System.out.println("searchMgo Image test "+ productMgo.toString());
		 * System.out.println(productMgo.getName() +" searchMgo Image test"+
		 * productMgo.getMainImage() + "product Name "+ productMgo.getName()); }
		 */

		/*
		 * long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		 * long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1; if
		 * (endCount > pageProducts.getTotalElements()) { endCount =
		 * pageProducts.getTotalElements(); }
		 */
		long startCount = 1;
		long endCount = pageProducts.getTotalPages();

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResult", listResult);

		return "product/search_result";
		// return "redirect:/";
	}

}