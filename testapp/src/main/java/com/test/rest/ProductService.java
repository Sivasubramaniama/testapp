package com.test.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.test.db.Address;
import com.test.db.AddressHome;
import com.test.db.BarcodeHome;
import com.test.db.Category;
import com.test.db.CategoryHome;
import com.test.db.Item;
import com.test.db.ItemHome;
import com.test.db.Parent;
import com.test.db.ParentHome;
import com.test.db.PersonHome;
import com.test.db.Product;
import com.test.db.ProductHome;
import com.test.excel.ReadExcel;
import com.test.excel.Record;
import com.test.rest.vo.Alternate;
import com.test.rest.vo.Details;
import com.test.rest.vo.Unknown;

@Path("/product")
public class ProductService {
	final static Logger logger = Logger.getLogger(ProductService.class);
	private static final String ITEMDONTEXISTS = "{\"errorCode\":\"ITEMDONTEXISTS\"}";
	private static final String UNKNOWN = "Unknown";
	ProductHome pdao = ProductHome.getInstance();
	ItemHome iDao = ItemHome.getInstance();
	BarcodeHome bDao = BarcodeHome.getInstance();
	ParentHome paDao = ParentHome.getInstance();
	AddressHome aDao = AddressHome.getInstance();

	@GET
	@Path("/cat/all")
	@Produces("application/json")
	public List<Category> getAllCategory(){
		List<Category> cats = CategoryHome.getInstance().findAll();
		return cats;
	}

	@GET
	@Path("/cat/{name}")
	@Produces("application/json")
	public Response getProducts(@PathParam("name") String catName){
		String json = null;
		List returnn = pdao.findProductByCategory(catName);
		List<Product> prods = new ArrayList<Product>();
		if(returnn != null){
			for(Object o : returnn){
				Product p = new Product();
				Map row = (Map)o;
				p.setProductName((String) row.get("product_name"));
	            prods.add(p);
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				json = mapper.writeValueAsString(prods);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
				
			} catch (JsonProcessingException e) {
				logger.error(e);
				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
			}
		}else{
			return Response.ok("{\"errorCode\":\"No_products_found_under_["+catName+"]\"}", MediaType.APPLICATION_JSON).build();
		}

		//return prods;
	
	}
	
	@GET
	@Path("/fetchToday")
	@Produces("application/json")
	public Response getItemWithoutProductParent(){
		//Product punknown = pdao.findByName(UNKNOWN);
		String json = null;
		// c.category_name, a.country, i.item_name, pa.parent_name, p.product_name, pa.boss
		String item_name = "item_name";
		String category_name="category_name";
		String country = "country";
		String parent_name = "parent_name";
		String product_name = "product_name";
		String boss = "boss";
		List unknowns = iDao.getItemByParentName(UNKNOWN);
		
		List<Unknown> itemList = new ArrayList<Unknown>();
		if(unknowns != null){
			for(Object o : unknowns){
				Unknown u = new Unknown();
				Map row = (Map)o;
				u.setItemName((String) row.get(item_name));
				u.setProductName((String) row.get(product_name));
				u.setParentName((String) row.get(parent_name));
				u.setCategoryName((String) row.get(category_name));
				u.setBoss((String) row.get(boss));
				u.setCountry((String) row.get(country));
				itemList.add(u);
			}	
		}else{
			return Response.ok("{\"errorCode\":\"No item with unknown Parent\"}", MediaType.APPLICATION_JSON).build();
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(itemList);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		} catch (JsonProcessingException e) {
			logger.error(e);
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
		
	}
	
	@GET
	@Path("/items")
	@Produces("application/json")
	public List<Item> getAllItem(){
		List<Item> items = iDao.findAll();
		return items;
	}
	
	@GET
	@Path("/alter/{iName}/{country}")
	@Produces("application/json")
	public Response getNativeAlternateProduct(@PathParam("iName") String itemName, @PathParam("country") String country){
		String json = null;
		Item entity = iDao.findItemByName(itemName);
		if(entity == null){
			json = "{\"errorCode\":\"Item Dont Exists\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
		Product psearch = entity.getProduct();
		if(psearch == null){
			json = "{\"errorCode\":\"PRODUCTDONTEXISTS\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
		Address aSearch = aDao.findByCountry(country);
		if(aSearch == null){
			json = "{\"errorCode\":\"Country ["+country+"] does not exist in the Database.\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
		List l = pdao.findAlternateCountryProduct(psearch, aSearch);
		List<Alternate> alters = new ArrayList<Alternate>();
		if(l != null){
			for(Object o : l){
				Alternate a = new Alternate();
				Map row = (Map)o;
				a.setProductId((Integer)row.get("p_id"));
				a.setCatgoryName((String) row.get("category_name"));
	            a.setProductName((String) row.get("product_name"));
				alters.add(a);
			}	
		}else{
			return Response.ok("{\"errorCode\":\"No Alternates found\"}", MediaType.APPLICATION_JSON).build();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(alters);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		} catch (JsonProcessingException e) {
			logger.error(e);
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
	}	
	
	
	@GET
	@Path("/alter/{iName}")
	@Produces("application/json")
	public Response getAllAlternateProduct(@PathParam("iName") String itemName){
		String json = null;
		
		Item entity = iDao.findItemByName(itemName);
		Product search = null;
		if(entity != null){
			search = entity.getProduct();
		}else{
			return Response.ok(ITEMDONTEXISTS, MediaType.APPLICATION_JSON).build();
		}

		if(search == null){
			return Response.ok(ITEMDONTEXISTS, MediaType.APPLICATION_JSON).build();
		}
		List l = pdao.findAlternateProduct(search);
		List<Alternate> alters = new ArrayList<Alternate>();
		if(l !=null){
		for(Object o : l){
			Alternate a = new Alternate();
			Map row = (Map)o;
			a.setProductId((Integer)row.get("p_id"));
			a.setCatgoryName((String) row.get("category_name"));
            a.setProductName((String) row.get("product_name"));
			alters.add(a);
		}
		}else{
			return Response.ok("{\"errorCode\":\"No Alternates found\"}", MediaType.APPLICATION_JSON).build();
		}
			
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(alters);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		} catch (JsonProcessingException e) {
			logger.error(e);
			return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
		}
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
				json = ITEMDONTEXISTS;
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
				logger.error(e);
				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();

			}
		
		}finally{
			
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
	@Path("/delete/{item}")
	@Produces("application/json")
	public Response deleteProduct(@PathParam("item") String item){
		String json = null;
		try {
			Item entity = iDao.findItemByName(item);
			Product p = pdao.findByName(entity.getProduct().getProductName());
			
			try{
				iDao.delete(entity);
				pdao.delete(p);
				json = "{\"message\":\"Deleted Successfully\"}";
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
			}catch(Exception e){
				logger.error(e);
				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
			
			}
			
		}finally{
			
		}
	}
	
	@GET
	@Path("/defaults")
	@Produces("application/json")
	public Response loadDefaults(){
		String json = null;
			
			try{
				List<Record> list = ReadExcel.readRowList(478);
				for(Record r : list){
					Item item = null;
					item = iDao.findItemByName(r.getItemName());
					try{
						if(item == null){
							item = new Item();
							item.setItemName(r.getItemName());
							item.setCreatedDate(new Date());
							item.setBarcode(bDao.findByName(UNKNOWN));
							item.setProduct(getProduct(r.getProductName(), r.getCategoryName(), r.getParentName(), r.getBossName(), r.getCountry()));
							iDao.persist(item);
						}
						
					}catch(ConstraintViolationException e){
						logger.error(e);
					}
				}
				json = "{\"message\":\"Successfully loaded\"}";
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
			}catch(Exception e){
				logger.error(e);
				return Response.ok(e.getMessage(), MediaType.APPLICATION_JSON).build();
			
			}
			
		
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/details/merge")
    public Response postMessage(Unknown unknown) throws Exception{
        logger.info(unknown);
        
        Product p = null;
		Item item = iDao.findItemByName(unknown.getItemName());
		if(item != null){
			p = getProduct(unknown.getProductName(), unknown.getCategoryName(), unknown.getParentName(), unknown.getBoss(), unknown.getCountry());
			item.setProduct(p);
			iDao.merge(item);
			logger.info("merged successfully");

			String json = "{\"message\":\"Successfully Merged\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
	   
		}else{
			logger.error("item not found");

			String json = "{\"errorCode\":\"Item not found\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		
		}

         }
	
	
	public static void main(String[] aargs){
		Unknown unknown = new Unknown();
		unknown.setItemName("Tiger");
		unknown.setProductName("Tiger");
		unknown.setParentName("Unknown");
		unknown.setCategoryName("Tea");
		unknown.setBoss("Boss");
		unknown.setCountry("India");
		ItemHome iDao = ItemHome.getInstance();
		ProductHome pDao = ProductHome.getInstance();
		Product p = null;
		Item item = iDao.findItemByName(unknown.getItemName());
		if(item != null){
			p = getProduct(unknown.getProductName(), unknown.getCategoryName(), unknown.getParentName(), unknown.getBoss(), unknown.getCountry());
			item.setProduct(p);
			iDao.merge(item);
			logger.info("merged successfully");
		}else{
			logger.error("item not found");
		}
		
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

