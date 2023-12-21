package com.shopme.productMgo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class CustomPage<ProductMgo> {

	public List<ProductMgo> productsMgo = new ArrayList<>();
	public long totalPages;
	public long totalElements;

	public CustomPage() {
		setTotalElements(0);
		setTotalPages(0);
		productsMgo = new ArrayList<>();
	}

	public CustomPage(List<ProductMgo> productsMgo) {
		super();
		this.productsMgo.addAll(productsMgo);
		totalElements = this.productsMgo.size();
	}

	public long size() {
		return productsMgo.size();
	}

	public List<ProductMgo> getProductsMgo() {
		return productsMgo;
	}

	public void setProductsMgo(List<ProductMgo> productsMgo1) {
		// this.productsMgo = productsMgo1;
		this.productsMgo.addAll(productsMgo1);
		totalElements = this.productsMgo.size();
	}

	// add to exhisting list

	public void addContent(List<ProductMgo> productsMgo1) {
		this.productsMgo.addAll(productsMgo1);
		totalElements = this.productsMgo.size();
	}

	public void addPageObject(Page<ProductMgo> page) {
		// this.productsMgo.addAll(page.getContent());
		addContent(page.getContent());

		if (totalPages < page.getTotalPages()) {
			totalPages = page.getTotalPages();
		}
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
