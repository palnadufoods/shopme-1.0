package com.shopme.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Test
	public void testCreateNewBrand()
	{
		Brand br=new Brand("Acer","brand_logo.jpg");
		Set<Category> categories=new HashSet<>();
		categories.add(entityManager.find(Category.class,7));
		br.setCategories(categories);
		Brand savedBr=brandRepository.save(br);
		assertThat(savedBr.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewBrandWithManyCategories()
	{
		Brand br=new Brand("Samsung","brand_logo.jpg");
		Set<Category> categories=new HashSet<>();
		categories.add(entityManager.find(Category.class,11));
		categories.add(entityManager.find(Category.class,15));
		
		br.setCategories(categories);
		Brand savedBr=brandRepository.save(br);
		assertThat(savedBr.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testFindAll()
	{
		List<Brand> listOfBrands=(List<Brand>) brandRepository.findAll();
		for(Brand br:listOfBrands)
		{
			System.out.print(br.getId()+" "+br.getName());
			for(Category cat:br.getCategories())
			{
				System.out.print(" "+cat.getName()+" "+cat.getId());	
			}
			
			//br.getCategories()
		}	
	}
	@Test
	public void findByNameAndId()
	{
		Brand br=brandRepository.findByNameAndId("tata",4);
		if(br!=null)
		{
			System.out.println(br.getName()+" "+br.getId());
		}
		assertThat(br.getId()).isGreaterThan(0);
	}
	
	@Test
	public void listAll()
	{
		List<Brand> brands=brandRepository.findAll();
		brands.forEach(System.out::println);
	}
	
}
