package com.test.db;
// Generated Dec 1, 2016 3:21:18 PM by Hibernate Tools 4.3.1.Final

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Person generated by hbm2java
 */
@XmlRootElement  
public class Product implements java.io.Serializable {

	private int id;
	private String name;

	public Product() {
	}

	public Product(int id, String name) {
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	
}
