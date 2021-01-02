package uk.dansiviter.microservices;

import javax.ws.rs.Path;

@Path("/hello")
public class RestResource {
	@Path("{name}")
	public String hello(String name) {
        return "Hello " + name + "!";
    }
}
