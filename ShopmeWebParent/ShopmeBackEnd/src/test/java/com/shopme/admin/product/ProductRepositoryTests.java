package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private TestEntityManager entityManger;

	@Test
	public void testCreateProduct() {
		Brand brand = entityManger.find(Brand.class, 10);
		Category category = entityManger.find(Category.class, 10);

		Product product = new Product();
		product.setName("Samsung Galaxy");
		product.setAlias("samsung_galaxy");
		product.setShortDescription("short Description for Samsung Mobile Phones");
		product.setFullDescription("This is a very Good Mobile Phone ");
		product.setBrand(brand);
		product.setCategory(category);
		product.setEnabled(true);
		product.setInStock(true);
		product.setCost(43000);
		product.setPrice(47000);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());

		Product savedProduct = repo.save(product);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);

	}

	@Test
	public void listAllProducts() {
		Iterable<Product> iterableProducts = repo.findAll();

		iterableProducts.forEach(System.out::println);
	}

	@Test
	public void testGetProduct() {
		Integer id = 2;
		Product product = repo.findById(id).get();
		assertThat(product.getId()).isGreaterThan(0);
	}

	@Test
	public void getProductByname() {
		Product product = repo.findByName("Acer Swift 3");
		assertThat(product.getId()).isGreaterThan(0);

	}

	@Test
	public void testSaveProductWithImages() {
		Product product = repo.findById(1).get();

		product.setMainImage("main image.png");
		product.addExtraImages("extra image 1.jpg");
		product.addExtraImages("extra image 2.jpg");
		product.addExtraImages("extra image 3.jpg");

		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}

	@Test
	public void testAddDetailsOfProduct() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		product.addDetail("Device Memory", "128GB");
		product.addDetail("CPU Model", "MediaTek");
		product.addDetail("Os", "Android 10");

		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();
	}

}
