package com.test.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.db.Address;
import com.test.db.Barcode;
import com.test.db.BarcodeHome;
import com.test.db.Item;
import com.test.db.ItemHome;
import com.test.db.Parent;
import com.test.db.ParentHome;
import com.test.db.Product;
import com.test.db.ProductHome;

@Path("/product")
public class ProductService {

	private static final String UNKNOWN = "Unknown";
	ProductHome pdao = ProductHome.getInstance();
	ItemHome iDao = ItemHome.getInstance();
	BarcodeHome bDao = BarcodeHome.getInstance();
	ParentHome paDao = ParentHome.getInstance();

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
		String json = null;
		try {
			Item entity = iDao.findItemByName(item);

			if(entity == null){
				Item def = iDao.getDefaultItem();
				def.setItemName(item);
				def.setBarcode(bDao.findByName(UNKNOWN));
				def.setProduct(pdao.findByName(UNKNOWN));
				//def.setIsActive(false);
				//def.setCreatedDate(new Date());
				iDao.persist(def);
				json = "{\"errorCode\":\"ITEMDONTEXISTS\"}";
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
	
			}
			
			Product p = entity.getProduct();
			Details d1 = new Details();
			d1.setProductName(p.getProductName());
			Parent pa = p.getParent();
			d1.setParentName(pa.getParentName());
			pa = paDao.findByName(pa.getParentName());
			Address a = pa.getAddress();
			d1.setCountry(a.getCountry());
			
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				json = mapper.writeValueAsString(d1);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();

			}
		
		}
//		catch (JsonProcessingException e) {
//			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
//		} 
		finally{
			
		} 
	}
	
	
	@GET
	@Path("/find/{name}")
	@Produces("application/json")
	public Response getProductIdByHib(@PathParam("name") String name){
		Product p = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try{
		 p = pdao.findByName(name);
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