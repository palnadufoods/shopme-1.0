package com.shopme.ProductMgoRepos.repo4;

import org.springframework.stereotype.Repository;

import com.shopme.ProductMgoRepos.ProductMgoRepository;

@Repository
public interface ProductMgoRepository4 extends ProductMgoRepository /*MongoRepository<ProductMgo, ObjectId>*/ {
	/*
	 * Page<ProductMgo> findAll(Pageable pageable);
	 * 
	 * @Query("{$or:[{'name': {$regex : ?0, $options: 'i'}}, {'alias': {$regex : ?0, $options: 'i'}}, {'shortDescription': {$regex : ?0, $options: 'i'}}, {'fullDescription': {$regex : ?0, $options: 'i'}}]}"
	 * ) Page<ProductMgo> searchProducts(String searchText, Pageable pageable);
	 * 
	 * @Query("{'id': ?0}") ProductMgo findByIdMysql(int id);
	 */
}
