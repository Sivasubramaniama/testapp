package com.test.rest.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Unknown {
	
	String categoryName;
	String country;
	String itemName;
	String parentName;
	String productName;
	String boss;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBoss() {
		return boss;
	}
	public void setBoss(String boss) {
		this.boss = boss;
	}
	
	@Override
	public String toString() {
		return "Unknowns [categoryName=" + categoryName + ", country=" + country + ", itemName=" + itemName
				+ ", parentName=" + parentName + ", productName=" + productName + ", boss=" + boss + "]";
	}

	
}
