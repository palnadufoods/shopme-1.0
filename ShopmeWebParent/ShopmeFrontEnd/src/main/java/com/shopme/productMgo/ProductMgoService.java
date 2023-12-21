package com.shopme.productMgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.common.ProductMgo.repo0.ProductMgoRepository0;
import com.shopme.common.ProductMgo.repo1.ProductMgoRepository1;
import com.shopme.common.ProductMgo.repo2.ProductMgoRepository2;
import com.shopme.common.ProductMgo.repo3.ProductMgoRepository3;
import com.shopme.common.ProductMgo.repo4.ProductMgoRepository4;
import com.shopme.common.ProductMgo.repo5.ProductMgoRepository5;
import com.shopme.common.ProductMgo.repo6.ProductMgoRepository6;
import com.shopme.common.ProductMgo.repo7.ProductMgoRepository7;
import com.shopme.common.ProductMgo.repo8.ProductMgoRepository8;
import com.shopme.common.ProductMgo.repo9.ProductMgoRepository9;

import com.shopme.common.entity.mongo.ProductMgo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class ProductMgoService {

	public static final int SEARCH_RESULTS_PER_PAGE = 4;

	@Autowired
	private ProductMgoRepository0 productMgoRepository0;

	@Autowired
	private ProductMgoRepository1 productMgoRepository1;

	@Autowired
	private ProductMgoRepository2 productMgoRepository2;

	@Autowired
	private ProductMgoRepository3 productMgoRepository3;

	@Autowired
	private ProductMgoRepository4 productMgoRepository4;

	@Autowired
	private ProductMgoRepository5 productMgoRepository5;

	@Autowired
	private ProductMgoRepository6 productMgoRepository6;

	@Autowired
	private ProductMgoRepository7 productMgoRepository7;

	@Autowired
	private ProductMgoRepository8 productMgoRepository8;

	@Autowired
	private ProductMgoRepository9 productMgoRepository9;

	/*
	 * public List<ProductMgo> getAllProducts() { return
	 * productMgoRepository.findAll(); }
	 */

	/*
	 * public List<ProductMgo> searchProducts(String searchText) { return
	 * productMgoRepository.searchProducts(searchText); }
	 */

	public <T> CustomPage<ProductMgo> searchProductsMgo(String searchText, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		// return repo.search(keyword, pageable);

		CustomPage<ProductMgo> customPage = new CustomPage<>();

		/*
		 * Page<ProductMgo> page0 = productMgoRepository0.searchProducts(searchText,
		 * pageable); // customPage.setProductsMgo(page0.getContent());
		 * customPage.addPageObject(page0); Page<ProductMgo> page1 =
		 * productMgoRepository1.searchProducts(searchText, pageable);
		 * customPage.addPageObject(page1);
		 */

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page0Future 0 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page0Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository0.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page0Future 0 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking0 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository1.searchProducts
					System.err.println("Exception in productMgoRepository0: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page1Future 1 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page1Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository1.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page1Future 1 " + LocalDateTime.now());
					customPage.addPageObject(page);
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking1 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository1.searchProducts
					System.err.println("Exception in productMgoRepository1: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... (Previous code)

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page2Future 2 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page2Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository2.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page2Future 2 " + LocalDateTime.now());
					customPage.addPageObject(page);
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking2 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository2.searchProducts
					System.err.println("Exception in productMgoRepository2: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page3Future 3 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page3Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository3.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page3Future 3 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking3 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository3.searchProducts
					System.err.println("Exception in productMgoRepository3: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page4Future 4 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page4Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository4.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page4Future 4 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking4 "+pro);
					 * }
					 */
					
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository4.searchProducts
					System.err.println("Exception in productMgoRepository4: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page5Future 5 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page5Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository5.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page5Future 5 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking5 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository5.searchProducts
					System.err.println("Exception in productMgoRepository5: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page6Future 6 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page6Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository6.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page6Future 6 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking6 "+pro);
					 * }
					 */
					
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository6.searchProducts
					System.err.println("Exception in productMgoRepository6: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page7Future 7 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page7Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository7.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page7Future 7 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking7 "+pro);
					 * }
					 */
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository7.searchProducts
					System.err.println("Exception in productMgoRepository7: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Repeat the same structure for each CompletableFuture up to 8

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page8Future 8 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page8Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository8.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page8Future 8 " + LocalDateTime.now());
					customPage.addPageObject(page);
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking8 "+pro);
					 * }
					 */
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository8.searchProducts
					System.err.println("Exception in productMgoRepository8: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		System.out.println("calling CompletableFuture<Page<ProductMgo>> page9Future 9 " + LocalDateTime.now());
		CompletableFuture<Page<ProductMgo>> page9Future = CompletableFuture
				.supplyAsync(() -> productMgoRepository9.searchProducts(searchText, pageable)).thenApply(page -> {
					System.out.println("Completed  page9Future 9 " + LocalDateTime.now());
					customPage.addPageObject(page);
					
					/*
					 * for(ProductMgo pro:page.getContent()) { System.out.println("Checking9 "+pro);
					 * }
					 */
					return page;
				}).exceptionally(ex -> {
					// Handle exception for productMgoRepository8.searchProducts
					System.err.println("Exception in productMgoRepository9: " + ex.getMessage());
					return null; // Return a default value or handle the exception as needed
				});

		// ... Continue your code as needed

		// You can continue adding more CompletableFuture for additional repositories

		// Add more CompletableFuture as needed

		// Wait for all CompletableFuture to complete (optional)
		CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(page0Future, page1Future, page2Future,
				page3Future, page4Future, page5Future, page6Future, page7Future, page8Future, page9Future);
		allOfFuture.join(); // Optional: Wait for all CompletableFuture to complete

		return customPage;

	}
}
