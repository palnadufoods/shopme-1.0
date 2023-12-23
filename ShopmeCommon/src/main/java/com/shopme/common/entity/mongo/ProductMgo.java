package com.shopme.common.entity.mongo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.shopme.common.Constants;

@Document(collection = "products")
public class ProductMgo {

	@Id
	private ObjectId _id;
	private Integer id;
	private String name;
	private String alias;
	@Field("short_description")
	private String shortDescription;
	@Field("full_description")
	private String fullDescription;
	
	@Field("created_time")
	private String createdTime;
	
	@Field("updated_time")
	private String updatedTime;
	
	private int enabled;
	@Field("in_stock")
	private int inStock;
	
	private int cost;
	private int price;
	
	@Field("discount_percent")
	private int discountPercent;
	
	private int length;
	private int width;
	private int height;
	private int weight;
	
	@Field("main_image")
	private String mainImage;
	
	@Field("category_id")
	private int categoryId;
	
	@Field("brand_id")
	private int brandId;
	
	@Field("review_count")
	private int reviewCount;
	
	@Field("average_rating")
	private int averageRating;

	public ProductMgo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	@Transient
	public String getMainImagePath() {

		// System.out.printf("id = %v, mainImage= ", id,mainImage);
		System.out.println("id = " + id + "mainImage = " + mainImage);
		System.out.println(this.getMainImage());

		if (id == null || mainImage == null)
			return "/images/image-thumbnail.png";
		return Constants.S3_BASE_URI+"/product-images/" + this.id + "/" + this.mainImage;
	}

	@Transient
	public String getURI() {
		return "/p/" + this.alias + "/";
	}

	@Transient
	public float getDiscountPrice() {
		if (discountPercent > 0) {
			//float f= price * ((100 - (float)discountPercent)/100);
			//System.out.println("getDiscountPrice="+ f +" price = "+ price);
			return price * ((100 - (float)discountPercent) / 100);
		}
		//System.out.println("getDiscountPrice No="+ this.price);
		return this.price;
	}

	@Transient
	public String getShortName() {
		if (name.length() > 70) {
			return name.substring(0, 70).concat("...");
		}
		return name;
	}

	@Override
	public String toString() {
		return "ProductMgo [_id=" + _id + ", id=" + id + ", name=" + name + ", alias=" + alias + ", shortDescription="
				+ shortDescription + ", fullDescription=" + fullDescription + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", enabled=" + enabled + ", inStock=" + inStock + ", cost=" + cost
				+ ", price=" + price + ", discountPercent=" + discountPercent + ", length=" + length + ", width="
				+ width + ", height=" + height + ", weight=" + weight + ", mainImage=" + mainImage + ", categoryId="
				+ categoryId + ", brandId=" + brandId + ", reviewCount=" + reviewCount + ", averageRating="
				+ averageRating + "]";
	}

	// constructors, getters, setters, and other methods
	
	
	
	
}
