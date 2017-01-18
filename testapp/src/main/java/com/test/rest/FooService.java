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
	@Path("/test")
	@Produces("text/plain")
    public String test() {
        return "Web Service Test";
    }
	
	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<Person> getPersonId(){
		List<Person> list = dao.findAll();
		return list;
	}
	
	@GET
	@Path("/create/{name}/{age}")
	@Produces("application/json")
	public Response createPerson(@PathParam("name") String name, @PathParam("age") String age){
		String json = null;
		
		Person pp = new Person();
		try{
					
			pp.setName(name);
			pp.setAge(Integer.parseInt(age));
				dao.persist(pp);
				
				
		}catch(ConstraintViolationException e){
			json ="{\"error\":\"Name ("+name+") already exists\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		}
		json ="{\"msg\":\"Created successfuly\"}";
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/update/{name}/{age}")
	@Produces("application/json")
	public Response updatePerson(@PathParam("name") String name, @PathParam("age") String age){
		String json = null;
		PersonHome dao = new PersonHome();
		Person pp = new Person();
		try{
			pp = dao.findByName(name);
			pp.setAge(Integer.parseInt(age));
			dao.update(pp);
			json ="{\"msg\":\"Updated successfuly\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		}catch(IllegalStateException e){
			json = "{\"error\":\"No Person exists with name = "+name+"\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
			
	}
	
	@GET
	@Path("/delete/{name}")
	@Produces("application/json")
	public Response deletePerson(@PathParam("name") String name){
		String json = null;
		PersonHome dao = new PersonHome();
		Person pp = new Person();
		try{
			pp = dao.findByName(name);
		}catch(IllegalStateException e){
			json = "{\"error\":\"No Person exists with name = "+name+"\"}";
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
		dao.delete(pp);
		json ="{\"msg\":\"deleted successfuly\"}";
		
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/find/{name}")
	@Produces("application/json")
	public Response getPersonIdByHib(@PathParam("name") String name){
		Person p = null;
		String json = null;
		PersonHome dao = new PersonHome();
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


	private void calculateBusiness() {

		int i = 0;
		String str = new String("one");
		
		do{
			//System.out.println(i++ +" "+str);
		}while(i<10000000);
		

		
	}
	
	
	
	
}