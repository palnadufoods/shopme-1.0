package com.shopme.admin.brand;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.RoleRepository;
import com.shopme.common.entity.Brand;

@Service
@Transactional
public class BrandService {

	public static final int BRANDS_PER_PAGE = 4;

	@Autowired
	public BrandRepository brandRepo;

	public List<Brand> listAll() {

		return (List<Brand>) brandRepo.findAll(Sort.by("name").ascending());
	}

	public Page<Brand> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
		if (sortField == null) {
			sortField = "name";
		}
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, BRANDS_PER_PAGE, sort);

		if (keyword != null)
			return brandRepo.findAll(keyword, pageable);
		return brandRepo.findAll(pageable);

	}

	public Brand save(Brand brand) {
		return brandRepo.save(brand);
	}

	public Brand updateAccount(Brand brandInForm) {
		Brand brandIndDb = brandRepo.findById(brandInForm.getId()).get();
		if (brandInForm.getLogo() != null) {
			brandIndDb.setLogo(brandInForm.getLogo());
		}
		brandIndDb.setName(brandInForm.getName());
		return brandRepo.save(brandIndDb);
	}

	public String checkUnique(int id1, String name) {

		Brand brand = brandRepo.findByName(name);
		if (brand == null) {
			return "ok";
		} else if (brand != null && brand.getId() == id1) {
			return "ok";
		} else {
			return "Duplicate Name";
		}
	}

	public Brand findById(Integer id) {
		return brandRepo.findById(id).get();
	}

	public void deleteById(int id) {
		brandRepo.deleteById(id);
	}

	public List<Brand> listAllBrands() {
		return brandRepo.findAll();
	}
}
