package com.shopme.admin.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;

@Repository
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Integer> {

	// public List<ProductImage> findByProduct(Product product);

	List<ProductImage> findByProduct(Product product);

//	@Query("SELECT Id from ProductImage p WHERE p.id=?1")
//	public List<Integer> findByProduct(Integer id);

}
