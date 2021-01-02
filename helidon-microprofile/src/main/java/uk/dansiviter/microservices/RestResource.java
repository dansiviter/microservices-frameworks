package uk.dansiviter.microservices;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hello/{name}")
public class RestResource {
	@GET
	@Produces(TEXT_PLAIN)
	public String hello(@PathParam("name") String name) {
        return ResponseUtil.create(name);
    }
}
