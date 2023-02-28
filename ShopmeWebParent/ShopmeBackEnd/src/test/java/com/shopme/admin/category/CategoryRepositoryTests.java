package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repo;

	@Test
	public void testCreateRootCategory() {
		Category cat = new Category("Gaming Laptops", "Gaming Laptops", "defualt.png");
		Category savedCategory = repo.save(cat);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(7);
		Category smartphone = new Category(10);

		Category iphone = new Category("iPhone", "iPhone", "default.png", smartphone);

		Category gaming = new Category("Gaming Laptops", "Gaming Laptops", "defualt.png", parent);
		repo.saveAll(List.of(gaming, iphone));
	}

	@Test
	public void testGetCategory() {
		Category cat = repo.findById(1).get();
		System.out.println(cat.getName());

		Set<Category> children = cat.getChildren();

		for (Category subCat : children) {
			System.out.println(subCat.getName());
		}

		assertThat(children.size()).isGreaterThan(0);
	}

	@Test
	public void printHierarchicalCategories() {
		Category cat = repo.findById(1).get();
		System.out.println(cat.getName());

		Set<Category> children = cat.getChildren();
		for (Category subCat : children) {
			System.out.println("--" + subCat.getName());
			printSubHierarchical(subCat, 1);
		}
	}

	public void printSubHierarchical(Category cat, int level) {
		Set<Category> children = cat.getChildren();
		for (Category subCat : children) {
			for (int i = 0; i <= level; i++)
				System.out.print("--");
			System.out.println(subCat.getName());
			printSubHierarchical(subCat, level + 1);
		}
	}

	@Test
	public void findByName() {
		String name = "computers";
		Category cat = repo.findByName(name);
		assertThat(cat).isNotNull();
		assertThat(cat.getName()).isEqualToIgnoringCase(name);
	}

	@Test
	public void findByAlias() {
		String alias = "computers";
		Category cat = repo.findByAlias(alias);
		assertThat(cat).isNotNull();
		assertThat(cat.getName()).isEqualToIgnoringCase(alias);
	}

	@Test
	public void findRootCategoris() {
		List<Category> list=repo.findRootCategories(Sort.by("name").ascending());
		SortedSet<Category> sortCat = new TreeSet<>(new Comparator<Category>() {
			@Override
			public int compare(Category o1, Category o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		sortCat.addAll(list);
		for(Category cat:sortCat)
		{
			System.out.println(cat.getName());
		}
		Category ct=sortCat.first();
		System.out.println(ct.getName()+"  ---------------------------------------- ");
		assertThat(sortCat.size()).isGreaterThan(0);
	}
	
	@Test
	public void sortedSet()
	{
	}
}