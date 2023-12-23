package com.shopme.ProductMgoRepos.repo9;

import org.springframework.stereotype.Repository;

import com.shopme.ProductMgoRepos.ProductMgoRepository;

@Repository
public interface ProductMgoRepository9 extends ProductMgoRepository {
	// Your custom queries, if any

	// Method to retrieve all products

	/*
	 * List<ProductMgo> findAll();
	 * 
	 * Page<ProductMgo> findAll(Pageable pageable);
	 * 
	 * // Method to search for products based on a text fields (e.g., name, //
	 * alias..........)
	 * 
	 * @Query("{$or:[{'name': {$regex : ?0, $options: 'i'}}, {'alias': {$regex : ?0, $options: 'i'}}, {'shortDescription': {$regex : ?0, $options: 'i'}}, {'fullDescription': {$regex : ?0, $options: 'i'}}]}"
	 * ) Page<ProductMgo> searchProducts(String searchText, Pageable pageable);
	 * 
	 * @Query("{'id': ?0}") ProductMgo findByIdMysql(int id);
	 */	
}