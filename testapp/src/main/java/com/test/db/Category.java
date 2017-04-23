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

/**
 * Category generated by hbm2java
 */
@XmlRootElement(name="category")
@XmlSeeAlso({Product.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class Category implements java.io.Serializable {

	private Integer CId;
	private String categoryName;
	private Date createdDate;
	@XmlElement(name="products")
	private Set products = new HashSet(0);

	public Category() {
	}	

	public Category(String categoryName, Date createdDate) {
		this.categoryName = categoryName;
		this.createdDate = createdDate;
	}

	public Category(String categoryName, Date createdDate, Set products) {
		this.categoryName = categoryName;
		this.createdDate = createdDate;
		this.products = products;
	}

	public Integer getCId() {
		return this.CId;
	}

	public void setCId(Integer CId) {
		this.CId = CId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set getProducts() {
		return this.products;
	}

	public void setProducts(Set products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [CId=" + CId + ", categoryName=" + categoryName + ", createdDate=" + createdDate
				+ "]";
	}
	

}
