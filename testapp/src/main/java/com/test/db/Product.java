package com.test.db;
// Generated Jan 21, 2017 2:12:31 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Product generated by hbm2java
 */
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Item.class})
public class Product implements java.io.Serializable {

	private Integer PId;
	@XmlTransient
	private Category category;
	@XmlTransient
	private Parent parent;
	private String productName;
	private Date createdDate;
	private boolean isActive;
	@XmlElement(name="items")
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

	@Override
	public String toString() {
		return "Product [PId=" + PId + ", category=" + category + ", parent=" + parent + ", productName=" + productName
				+ ", createdDate=" + createdDate + ", isActive=" + isActive +  "]";
	}

}
