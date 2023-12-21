package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.productMgo.ProductMgoService1;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;
import com.shopme.common.exception.ProductNotFoundException;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductMgoService1 productMgoService1;

	public static final int PRODUCTS_PER_PAGE = 5;

	public List<Product> listAll() {
		List<Product> list = (List<Product>) repo.findAll();
		return list;
	}

	/*
	 * public Page<Product> listByPage(int pageNumber, String sortField, String
	 * sortDir, String keyword, Integer categoryId) { if (sortField == null) {
	 * sortField = "name"; } Sort sort = Sort.by(sortField); sort =
	 * sortDir.equals("asc") ? sort.ascending() : sort.descending(); Pageable
	 * pageable = PageRequest.of(pageNumber - 1, PRODUCTS_PER_PAGE, sort);
	 * 
	 * Page<Product> page = null; if (keyword != null && !keyword.isEmpty()) { if
	 * (categoryId != null && categoryId > 0) { String categoryIdMatch = "-" +
	 * String.valueOf(categoryId) + "-"; page = repo.searchInCategory(categoryId,
	 * categoryIdMatch, keyword, pageable); } else { return repo.findAll(keyword,
	 * pageable); } } else { if (categoryId != null && categoryId > 0) { String
	 * categoryIdMatch = "-" + String.valueOf(categoryId) + "-"; page =
	 * repo.findAllInCategory(categoryId, categoryIdMatch, pageable); } else { page
	 * = repo.findAll(pageable); } }
	 * 
	 * // return repo.findAll(pageable); return page; }
	 */
	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<Product> page = null;

		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			} else {
				page = repo.findAll(keyword, pageable);
			}
		} else {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			} else {
				page = repo.findAll(pageable);
			}
		}

		helper.updateModelAttributes(pageNum, page);

	}

	public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();		
		Page<Product> page = repo.searchProductsByName(keyword, pageable);		
		helper.updateModelAttributes(pageNum, page);
	}
	
	// findAllInCategory

	public Product save(Product product) throws Exception {

		/*
		 * for (int i = 0; i < 10; i++) System.out.println();
		 * System.out.println("product for checking .....>" + product); for (int i = 0;
		 * i < 10; i++) System.out.println();
		 */

		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		} else {
			Product pr = repo.findById(product.getId()).get();
			product.setCreatedTime(pr.getCreatedTime());
		}

		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			product.setAlias(product.getName().replaceAll(" ", "-"));
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}

		product.setUpdatedTime(new Date());
		// repo methods will always be first because of rollback.... 
		Product savedProduct = repo.save(product);
		productMgoService1.save(product);

		return savedProduct;
	}

	public void saveProductPrice(Product productInForm) throws Exception {
		Product productInDB = repo.findById(productInForm.getId()).get();
		productInDB.setCost(productInForm.getCost());
		productInDB.setPrice(productInForm.getPrice());
		productInDB.setDiscountPercent(productInForm.getDiscountPercent());
		// repo methods will always be first because of rollback.... 
		repo.save(productInDB);
		productMgoService1.save(productInDB);
		
	}

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);

		if (isCreatingNew) {
			if (productByName != null)
				return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		return "OK";
	}

	public void updateProductEnabledStatus(Integer id, boolean enabled) throws Exception {

		// repo methods will always be first because of rollback.... 
		repo.updateEnabledStatus(id, enabled);
		productMgoService1.updateEnabledStatus(id, enabled);

	}

	public void delete(Integer id) throws Exception {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not find any product with Id " + id);
		}
		// repo methods will always be first because of rollback.... 
		repo.deleteById(id);
		productMgoService1.deleteProductMgo(id);
	}

	public Product get(Integer id) throws ProductNotFoundException {
		Product product = null;
		try {
			product = repo.findById(id).get();
			return product;
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}

	}

	public List<ProductImage> getProductImagesByProduct(Product product) {
		// return productImageRepository.findByProduct(product);
		return null;
	}

	public void deleteProductImageById(Integer id) throws Exception {
		productImageRepository.deleteById(id);
	}

}
