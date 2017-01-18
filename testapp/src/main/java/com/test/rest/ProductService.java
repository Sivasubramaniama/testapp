package com.test.rest;

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
	@Path("/test")
	@Produces("text/plain")
    public String test() {
        return "Web Service Test";
    }
	
	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<Product> getPersonId(){
		List<Product> list = dao.findAll();
		return list;
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