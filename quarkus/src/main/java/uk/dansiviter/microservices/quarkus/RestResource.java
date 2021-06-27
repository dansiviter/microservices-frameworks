package uk.dansiviter.microservices.quarkus;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

@Path("/")
public class RestResource {
	@Inject
	PersonRepo repo;

	@GET
	@Path("hello/{name}")
	public String hello(@PathParam("name") String name) {
		return ResponseUtil.create(name);
	}

	@GET
	@Path("hello/error")
	public String error() {
		throw new CustomException();
	}

	@GET
	@Path("/people/{name}")
	@Produces(APPLICATION_JSON)
	public Optional<Person> people(@PathParam("name") String name) {
		return this.repo.findByIdOptional(name);
	}
}
