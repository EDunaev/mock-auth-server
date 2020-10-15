package de.dunaev.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/mock")
public class MockAuthResource {
	private String tokenAdmin = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkV2Z2VueSBEdW5hZXYiLCJhZG1pbiI6dHJ1ZX0.-ngk8U-KW-GdJQxgehHqrnZsg0bcEykaIzw7P09YlAk";
	private String tokenUser = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkxIiwibmFtZSI6IkVsZW5hIE9raXNoZXZhIiwiYWRtaW4iOmZhbHNlfQ.sMDs1TXs9Hyw7NyI4iFRjdRY1zUc9qO6HxYRMBoR0pM";

	@Path("/authenticate")
	@POST
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public Response authenticate(UserTO userTO) throws Exception {
		Response.ResponseBuilder rb;
		if (userTO.getEmail().equals("dunaeve@yahoo.com") && userTO.getPassword().equals("1234")) {
			userTO.setToken(tokenAdmin);
			rb = Response.ok(userTO);
			return rb.build();
		}
		if (userTO.getEmail().equals("okishevaelena@gmail.com") && userTO.getPassword().equals("1234")) {
			userTO.setToken(tokenUser);
			rb = Response.ok(userTO);
			return rb.build();
		}
		return null;
	}

	@Path("/orders")
	@GET
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public Response orders(@Context HttpHeaders headers) throws Exception {
		MultivaluedMap<String, String> rh = headers.getRequestHeaders();
		
		String tokenFromClient = rh.get("authorization").get(0);
		String token = "Bearer " + this.tokenAdmin;
		if(tokenFromClient.equals(token)) {
			Response.ResponseBuilder rb;
			int[] result  = {1,2,3};
			rb = Response.ok(result);
			return rb.build();
		}
		return  Response.status(401).build();
		
	}
}
