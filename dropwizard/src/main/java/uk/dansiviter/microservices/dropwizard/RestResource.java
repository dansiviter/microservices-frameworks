package uk.dansiviter.microservices.dropwizard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

@Path("/hello")
public class RestResource {
	@GET
	@Path("{name}")
	public String hello(@PathParam("name") String name) {
		return ResponseUtil.create(name);
	}

	@GET
	@Path("error")
	public String error() {
		throw new CustomException();
	}
}
