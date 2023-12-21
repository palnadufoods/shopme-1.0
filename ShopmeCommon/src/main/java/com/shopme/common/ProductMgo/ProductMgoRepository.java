package com.shopme.common.ProductMgo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.mongo.ProductMgo;

@Repository
public interface ProductMgoRepository extends MongoRepository<ProductMgo, ObjectId> {

	Page<ProductMgo> findAll(Pageable pageable);

	// Method to search for products based on a text fields (e.g., name,
	// alias..........)

	@Query("{$and: [{$or:[{'name': {$regex : ?0, $options: 'i'}}, {'alias': {$regex : ?0, $options: 'i'}}, {'shortDescription': {$regex : ?0, $options: 'i'}}, {'fullDescription': {$regex : ?0, $options: 'i'}}]}, {'enabled': 1}]}")
	// @Query("{$or:[{'name': {$regex : ?0, $options: 'i'}}, {'alias': {$regex : ?0,
	// $options: 'i'}}, {'shortDescription': {$regex : ?0, $options: 'i'}},
	// {'fullDescription': {$regex : ?0, $options: 'i'}}]}")
	Page<ProductMgo> searchProducts(String searchText, Pageable pageable);

	@Query("{'id': ?0}")
	ProductMgo findByIdMysql(int id);

	/*
	 * @Modifying
	 * 
	 * @Query("{$and: [{'_id': ?0}, {'enabled': ?1}]}") void
	 * updateEnabledStatus(ObjectId productId, int enabled);
	 */

}
