package com.shopme.admin.productMgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.ProductMgo.ProductMgoRepository;
import com.shopme.common.ProductMgo.repo0.ProductMgoRepository0;
import com.shopme.common.ProductMgo.repo1.ProductMgoRepository1;
import com.shopme.common.ProductMgo.repo2.ProductMgoRepository2;
import com.shopme.common.ProductMgo.repo3.ProductMgoRepository3;
import com.shopme.common.ProductMgo.repo4.ProductMgoRepository4;
import com.shopme.common.ProductMgo.repo5.ProductMgoRepository5;
import com.shopme.common.ProductMgo.repo6.ProductMgoRepository6;
import com.shopme.common.ProductMgo.repo7.ProductMgoRepository7;
import com.shopme.common.ProductMgo.repo8.ProductMgoRepository8;
import com.shopme.common.ProductMgo.repo9.ProductMgoRepository9;
import com.shopme.common.entity.Product;
import com.shopme.common.entity.mongo.ProductMgo;

@Service
//@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
public class ProductMgoService1 {

	@Autowired
	private ProductMgoRepository0 productMgoRepository0;

	@Autowired
	private ProductMgoRepository1 productMgoRepository1;

	@Autowired
	private ProductMgoRepository2 productMgoRepository2;

	@Autowired
	private ProductMgoRepository3 productMgoRepository3;

	@Autowired
	private ProductMgoRepository4 productMgoRepository4;

	@Autowired
	private ProductMgoRepository5 productMgoRepository5;

	@Autowired
	private ProductMgoRepository6 productMgoRepository6;

	@Autowired
	private ProductMgoRepository7 productMgoRepository7;

	@Autowired
	private ProductMgoRepository8 productMgoRepository8;

	@Autowired
	private ProductMgoRepository9 productMgoRepository9;

//	ProductMgoRepository productMgoRepository;

	public void save(Product product) {

		ProductMgoRepository productMgoRepository = getproperRepository(product.getId());
		ProductMgo productMgo = productMgoRepository.findByIdMysql(product.getId());
		System.out.println("Pro1 interface " + productMgo);

		if (productMgo == null) {
			productMgo = new ProductMgo();
			productMgo.setId(product.getId());
			CopyProductTOProductMgo(product, productMgo);
			productMgoRepository.insert(productMgo);
		} else {
			CopyProductTOProductMgo(product, productMgo);
			productMgoRepository.save(productMgo);
		}

	}

	private ProductMgoRepository getproperRepository(Integer id) {

		Integer id1 = id % 10;
		if (id1 == 0)
			return productMgoRepository0;
		else if (id1 == 1)
			return productMgoRepository1;
		else if (id1 == 2)
			return productMgoRepository2;
		else if (id1 == 3)
			return productMgoRepository3;
		else if (id1 == 4)
			return productMgoRepository4;
		else if (id1 == 5)
			return productMgoRepository5;
		else if (id1 == 6)
			return productMgoRepository6;
		else if (id1 == 7)
			return productMgoRepository7;
		else if (id1 == 8)
			return productMgoRepository8;
		else
			return productMgoRepository9;
	}

	private void CopyProductTOProductMgo(Product product, ProductMgo productMgo) { // TODO Auto-generated method stub
																					// productMgo.setName(product.getName());
		productMgo.setAlias(product.getAlias());
		productMgo.setAverageRating((int) product.getAverageRating());
		productMgo.setFullDescription(product.getFullDescription());
		productMgo.setShortDescription(product.getShortDescription());
		productMgo.setEnabled(product.isEnabled() ? 1 : 0);
		productMgo.setInStock(product.isInStock() ? 1 : 0);
		productMgo.setCost((int) product.getCost());
		productMgo.setPrice((int) product.getPrice());
		productMgo.setDiscountPercent((int) product.getDiscountPercent());
		productMgo.setMainImage(product.getMainImage());
		productMgo.setBrandId(product.getBrand().getId());
		productMgo.setCategoryId(product.getCategory().getId());
		productMgo.setName(product.getName());
		// etc ..
		System.out.println("sita " + productMgo);
	}

	public void updateEnabledStatus(Integer id, boolean enabledStatus) throws Exception {

		ProductMgoRepository productMgoRepository = getproperRepository(id);
		ProductMgo productMgo = productMgoRepository.findByIdMysql(id);
		productMgo.setEnabled(enabledStatus ? 1 : 0);
		//throw new Exception();
		if (productMgo != null) {
			//productMgoRepository.save(null);
			productMgoRepository.save(productMgo);
		}

	}

	public void deleteProductMgo(Integer id) throws Exception {

		ProductMgoRepository productMgoRepository = getproperRepository(id);
		ProductMgo productMgo = productMgoRepository.findByIdMysql(id);
		if (productMgo != null) {
			productMgoRepository.deleteById(productMgo.get_id());
		}

		//throw new Exception("Testing it should not be delted  ");
	}

}
