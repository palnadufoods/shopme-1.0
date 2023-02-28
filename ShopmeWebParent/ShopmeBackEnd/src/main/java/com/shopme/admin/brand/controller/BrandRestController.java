package com.shopme.admin.brand.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.brand.BrandService;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@RestController
public class BrandRestController {

	@Autowired
	public BrandService service;

	@PostMapping("/brand/check_unique")
	public String checkDuplicateName(@Param("id") String id, @Param("name") String name) {

		int id1 = 0;
		if (id == null || id.isEmpty())
			id1 = 0;
		else
			id1 = Integer.parseInt(id);
		return service.checkUnique(id1, name);
	}

	@GetMapping("/brands/{id}/categories")
	public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) {

		List<CategoryDTO> listOfDTOCategories = new ArrayList<CategoryDTO>();
		Brand brand = service.findById(brandId);
		Set<Category> listOfCats = brand.getCategories();

		for (Category cat : listOfCats) {
			CategoryDTO categoryDTO = new CategoryDTO(cat.getId(), cat.getName());
			listOfDTOCategories.add(categoryDTO);
		}
		return listOfDTOCategories;
	}
	
	
}
