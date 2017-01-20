package com.test.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.db.Product;
import com.test.db.ProductHome;

@Path("/product")
public class ProductService {
	ProductHome dao = new ProductHome();

	@GET
	@Path("/fetchToday")
	@Produces("application/json")
	public Response getItemWithoutProductParent(){
		ObjectMapper mapper = new ObjectMapper();
		
		List<String> all = new ArrayList<String>();
		all.add("item1");
		all.add("item2");
		all.add("item3");
		String json = null;
		try {
			json = mapper.writeValueAsString(all);
		} catch (JsonProcessingException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/fetch/{item}")
	@Produces("application/json")
	public Response getProductParentByItem(@PathParam("item") String item){
		//fetch a db call for item
		 //if
			//do something
		//else
			//insert into db and return empty
		
		Details d1 = new Details();
		d1.setCountry("India");
		d1.setProductName("Board");
		d1.setParentName("Godrej");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(d1);
		} catch (JsonProcessingException e) {
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/find/{name}")
	@Produces("application/json")
	public Response getProductIdByHib(@PathParam("name") String name){
		Product p = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try{
		 p = dao.findByName(name);
		 json = mapper.writeValueAsString(p);
		 return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}catch(IllegalStateException e){
			json = "{\"error\":\"No record found for name = "+name+"\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			json = "{\"error\":\"Internal error occured \"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
		
	}
	
}

class Details{
	String productName;
	String parentName;
	String country;

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}