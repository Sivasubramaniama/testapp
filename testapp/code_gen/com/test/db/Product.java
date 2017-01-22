package com.test.db;
// Generated Jan 21, 2017 2:12:31 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Product generated by hbm2java
 */
public class Product implements java.io.Serializable {

	private Integer PId;
	private Category category;
	private Parent parent;
	private String productName;
	private Date createdDate;
	private boolean isActive;
	private Set items = new HashSet(0);

	public Product() {
	}

	public Product(Category category, Parent parent, String productName, Date createdDate, boolean isActive) {
		this.category = category;
		this.parent = parent;
		this.productName = productName;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}

	public Product(Category category, Parent parent, String productName, Date createdDate, boolean isActive,
			Set items) {
		this.category = category;
		this.parent = parent;
		this.productName = productName;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.items = items;
	}

	public Integer getPId() {
		return this.PId;
	}

	public void setPId(Integer PId) {
		this.PId = PId;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set getItems() {
		return this.items;
	}

	public void setItems(Set items) {
		this.items = items;
	}

}