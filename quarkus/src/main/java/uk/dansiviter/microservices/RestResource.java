package uk.dansiviter.microservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class RestResource {
	@GET
	@Path("{name}")
	public String hello(@PathParam("name") String name) {
				return "Hello " + name + "!";
		}
}
