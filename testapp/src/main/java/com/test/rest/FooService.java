package com.test.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.db.Person;
import com.test.db.PersonHome;

@Path("/person")
public class FooService {
	PersonHome dao = new PersonHome();

	@GET
	@Path("/create/{name}/{email}/{country}")
	@Produces("application/json")
	public Response pushPerson(@PathParam("name") String name, @PathParam("email") String email, @PathParam("country") String country){
		String json = null;
		Person p = null;
		p = dao.findByEmail(email);
		if(p!=null){
			p.setName(name);
			p.setCountry(country);
			dao.merge(p);
			json = "{\"message\":\"Updated Successfully\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}else{
			p = new Person();
			p.setCountry(country);
			p.setEmail(email);
			p.setName(name);
			
			try{
				dao.persist(p);	
				json = "{\"message\":\"Successfully Saved\"}";
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			}catch(Exception e){
				json = "{\"error\":\""+e.getMessage()+"\"}";
				return Response.ok(json, MediaType.APPLICATION_JSON).build();	
			}

		}
		
	}
	
	
	@GET
	@Path("/test")
	@Produces("text/plain")
    public String test() {
        return "Web Service Test";
    }
	
	
}