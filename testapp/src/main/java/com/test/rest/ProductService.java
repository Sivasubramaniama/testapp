package com.test.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.test.db.AddressHome;
import com.test.db.BarcodeHome;
import com.test.db.Category;
import com.test.db.CategoryHome;
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
	@Path("/details/create/{item}/{productName}/{categoryName}/{parentName}/{boss}/{country}")
	@Produces("application/json")
	public Response createDetailsForItem(@PathParam("item") String itemName, 
			@PathParam("productName") String productName, 
			@PathParam("categoryName") String categoryName,
			@PathParam("parentName") String parentName,
			@PathParam("boss") String boss,
			@PathParam("country") String country){

		Item itemEntity = iDao.findItemByName(itemName);
		Product prodEntity = getProduct(productName, categoryName, parentName, boss, country);
		if(prodEntity != null){
				if(itemEntity !=null){
					itemEntity.setProduct(prodEntity);
				}else{
					System.out.println("itemEntity is empty or null");
				}
		}
		iDao.merge(itemEntity);
		System.out.println(iDao.findItemByName(itemName));
		
		String json = "{\"message\":\"Details Saved Successfully\"}";
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
	
	public static void main(String[] aargs){
		//fill details of a item.
		//get all item without product details
		ItemHome iDao = ItemHome.getInstance();
		ProductHome pDao = ProductHome.getInstance();
		String productName = "HUL";
		String categoryName = "Washing";
		String parentName = "HUL";
		String country = "U.S.A";
		String itemName = "cocacola";
//		Parent pa = getParent(parentName, UNKNOWN, country);
//		System.out.println(pa);
//		Category cat = getCategory(categoryName);
//		System.out.println(cat);
		Item itemArial = iDao.findItemByName(itemName);
		Product prodArial = getProduct(productName, categoryName, parentName, UNKNOWN, country);
		if(prodArial != null){
				if(itemArial !=null){
					itemArial.setProduct(prodArial);
				}else{
					System.out.println("itemArial is empty or null");
				}
		}
		iDao.merge(itemArial);
		System.out.println(iDao.findItemByName(itemName));
		
		//List<Integer> list = iDao.getItemWithoutProduct();
		//Iterator<Integer> itr = list.iterator();
//		while (itr.hasNext()) {
//			Integer i = (Integer) itr.next();
//			Product p = pDao.findByName(productName);
//			//product already exists
//			if(p != null){
//				Item i1 = iDao.findById(i);
//				if(i1.getItemName().equals("pepsi")){
//					i1.setProduct(p);
//					iDao.merge(i1);
//				}
//			}else{
//				Product p1 = new Product();
//				p1.setIsActive(true);
//				
//				
//				
//			}
//			
//		}
		
	}

	private static Product getProduct(String productName,String categoryName, String parentName, String boss, String country) {
		ProductHome pDao = ProductHome.getInstance();
		Product p1 = pDao.findByName(productName);
		if(p1 == null){
			Product pNew = new Product();
			pNew.setProductName(productName);
			pNew.setParent(getParent(parentName, boss, country));
			pNew.setCategory(getCategory(categoryName));
			pNew.setCreatedDate(new Date());
			pDao.persist(pNew);
			return pDao.findByName(productName);
		}else{
			return p1;
		}
	}

	private static Category getCategory(String categoryName) {
		CategoryHome cDao = CategoryHome.getInstance();
		Category c = cDao.findByName(categoryName);
		if(c==null){
			Category cNew = new Category();
			cNew.setCategoryName(categoryName);
			cNew.setCreatedDate(new Date());
			cDao.persist(cNew);
			return cDao.findByName(categoryName);
			
		}else{
			return c;
		}
	}

	private static Parent getParent(String parentName, String boss, String country) {
		ParentHome paDao = ParentHome.getInstance();
		
		Parent pa = paDao.findByName(parentName);
		if(pa == null)
		{
			Parent paNew = new Parent();
			paNew.setParentName(parentName);
			paNew.setBoss(boss);
			paNew.setCreatedDate(new Date());
			paNew.setIsActive(true);
			paNew.setAddress(getAddress(country));
			
			paDao.persist(paNew);
			return paDao.findByName(parentName);
		}else{
			return pa;
		}
	}

	private static Address getAddress(String country) {
		AddressHome aDao = AddressHome.getInstance();
		Address a =aDao.findByCountry(country);
		if(a == null){
			Address aNew = aDao.getDefaultAddress();
			aNew.setCountry(country);
			aDao.persist(aNew);
			return aDao.findByCountry(country);
		}else{
			return a;
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