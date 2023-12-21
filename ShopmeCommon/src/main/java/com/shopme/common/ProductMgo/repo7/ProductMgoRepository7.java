package com.shopme.common.ProductMgo.repo7;

import java.util.List;

import org.bson.types.ObjectId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.shopme.common.ProductMgo.ProductMgoRepository;
import com.shopme.common.entity.mongo.ProductMgo;

@Repository
public interface ProductMgoRepository7 extends  ProductMgoRepository/*MongoRepository<ProductMgo, ObjectId>*/ {
	/*
	 * Page<ProductMgo> findAll(Pageable pageable);
	 * 
	 * @Query("{$or:[{'name': {$regex : ?0, $options: 'i'}}, {'alias': {$regex : ?0, $options: 'i'}}, {'shortDescription': {$regex : ?0, $options: 'i'}}, {'fullDescription': {$regex : ?0, $options: 'i'}}]}"
	 * ) Page<ProductMgo> searchProducts(String searchText, Pageable pageable);
	 * 
	 * @Query("{'id': ?0}") ProductMgo findByIdMysql(int id);
	 */
}
