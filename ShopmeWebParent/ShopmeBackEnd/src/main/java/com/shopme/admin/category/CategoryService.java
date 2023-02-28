
package com.shopme.admin.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;

@Service
@Transactional
public class CategoryService {

	public static final int CATEGORIES_PER_PAGE = 4;
	public static long totalElements;
	public static long totalPages;
	@Autowired
	public CategoryRepository repo;
	public String sortDir = "asc";

	public List<Category> listAll() {
		List<Category> list = (List<Category>) repo.findAll();
		return list;
	}

	public List<Category> listByPage(int pageNumber, String sortDir) {
		Sort sort = Sort.by("name");
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORIES_PER_PAGE, sort);
		Page<Category> page = repo.findRootCategories(pageable);
		totalElements = page.getTotalElements();
		totalPages = page.getTotalPages();
		List<Category> catsInForm = new ArrayList<Category>();
		this.sortDir = sortDir;
		for (Category cat : page.getContent()) {
			getSubHierarchicalSorting(cat, 0, catsInForm, 0);
		}
		return catsInForm;
		// return repo.findRootCategories(pageable);
	}

	public List<Category> search(int pageNumber, String sortDir, String keyword) {
		Sort sort = Sort.by("name");
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORIES_PER_PAGE, sort);
		Page<Category> page = repo.search(keyword, pageable);
		totalElements = page.getTotalElements();
		totalPages = page.getTotalPages();
		List<Category> catsInForm = new ArrayList<Category>();
		this.sortDir = sortDir;
		for (Category cat : page.getContent()) {
			searchGetHierarchical(cat, catsInForm);
		}
		return catsInForm;
		// return repo.findRootCategories(pageable);
	}

	private void searchGetHierarchical(Category cat, List<Category> catsInForm) {
		if (cat == null)
			return;
		String str = "";
		Category cat1 = cat;
		while (cat1.getParent() != null) {
			str += "--";
			cat1 = cat1.getParent();
		}
		catsInForm.add(Category.copyAll(cat, str));
	}

	public List<Category> listCatsInSorting(int i, String sortDir) {

		List<Category> catsInDB = null;
		this.sortDir = sortDir;

		if (sortDir.equalsIgnoreCase("asc"))
			catsInDB = (List<Category>) repo.findRootCategories(Sort.by("name").ascending());
		else
			catsInDB = (List<Category>) repo.findRootCategories(Sort.by("name").descending());
		List<Category> catsInForm = new ArrayList<Category>();
		for (Category cat : catsInDB) {
			getSubHierarchicalSorting(cat, 0, catsInForm, i);
		}
		return catsInForm;
	}

	public void getSubHierarchicalSorting(Category cat, int level, List<Category> catsInForm, int i) {
		if (cat != null) {
			hierarchy(cat, level, catsInForm, i);
			Set<Category> childrenSorted = new TreeSet<>(new Comparator<Category>() {
				@Override
				public int compare(Category o1, Category o2) {
					if (sortDir.equalsIgnoreCase("asc"))
						return o1.getName().compareTo(o2.getName());
					else
						return o2.getName().compareTo(o1.getName());
				}
			});
			childrenSorted.addAll(cat.getChildren());
			for (Category subCat : childrenSorted) {
				getSubHierarchicalSorting(subCat, level + 1, catsInForm, i);
			}
		}
	}

	private void hierarchy(Category cat, int level, List<Category> catsInForm, int i) {
		String str = "";
		for (int j = 0; j < level; j++)
			str += "--";
		// cat.setName(str + cat.getName());
		if (i == 1) {
			catsInForm.add(Category.CopyIdAndName(cat, str));
		} else {
			catsInForm.add(Category.copyAll(cat, str));
		}
	}

	public Category save(Category category) {
		Category parent = category.getParent();
		if (parent != null) {
			String allParentIds = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
			allParentIds += String.valueOf(parent.getId()) + "-";
			category.setAllParentIDs(allParentIds);
		}
		return repo.save(category);
	}

	public Category get(int id) {
		return repo.findById(id).get();
	}

	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);
		for (int i = 0; i < 5; i++)
			System.out.println(isCreatingNew);
		Category catByName = repo.findByName(name);
		Category catByAlias = repo.findByAlias(alias);

		if (isCreatingNew) {
			if (catByName != null) {
				return "Duplicate Name";
			}
			if (catByAlias != null) {
				return "Duplicate Alias";
			}
		} else {
			if (catByName != null && catByName.getId() != id) {
				System.out.println(catByName.getId() + "      and1     " + id);
				return "Duplicate Name";
			}
			if (catByAlias != null && catByAlias.getId() != id) {
				System.out.println(catByAlias.getId() + "      and2     " + id);
				return "Duplicate Alias";
			}
		}
		return "ok";
	}

	public void UpdateEnabledStatus(Integer id, boolean b) {
		repo.updateEnabledStatus(id, b);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	public List<Category> listCategoriesUsedInForm() {
		// TODO Auto-generated method stub
		return null;
	}
}