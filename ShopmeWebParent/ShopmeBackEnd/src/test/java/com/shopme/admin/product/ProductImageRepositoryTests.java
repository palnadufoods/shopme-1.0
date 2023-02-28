package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Product;
import com.shopme.common.entity.ProductImage;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductImageRepositoryTests {

	@Autowired
	private ProductImageRepository productImageRepo;

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void getProductImageIds() {
		Integer id = 16;
		Product product = productRepository.findById(id).get();

		List<ProductImage> productImage = productImageRepo.findByProduct(product);
		
		for (ProductImage pi : productImage) {
			System.out.println(pi);
		}
		assertThat(productImage.size()).isGreaterThan(0);

	}

}
